package edu.bit.dlde;

/**
 * 
 */

import java.io.BufferedReader;
import java.io.CharArrayReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import sun.io.ByteToCharConverter;
import edu.bit.dlde.extractor.BlockExtractor;
import edu.bit.dlde.extractor.SimpleHtmlExtractor;

/**
 * @author Hz
 * 
 */
@SuppressWarnings( { "deprecation", "restriction" })
public class HtmlFile {
	/**
	 * 
	 */
	private String hostName = null;
	private String netFilePath = null;
	private String filePath = null;
	private String charSet = "utf-8";// default value
	private byte[] bytes = null;
	private String source = null;
	private String content = null, title = null;
	private BufferedReader bufferedReader = null;

	static public enum ParseMethod {
		SimpleHtmlExtracter, BlockExtractor
	};

	private ParseMethod parseMethod = ParseMethod.SimpleHtmlExtracter;

	public ParseMethod getParseMethod() {
		return parseMethod;
	}

	public String getHostName() {
		return hostName;
	}

	public String getNetFilePath() {
		return netFilePath;
	}

	public String getCharSet() {
		return charSet;
	}

	public void setCharSet(String charSet) throws UnsupportedEncodingException {
		this.charSet = charSet;
		if (source != null) {
			source = new String(bytes, charSet);
			parse();
		}
	}

	public void setParseMethod(ParseMethod _parseMethod)
			throws UnsupportedEncodingException {
		this.parseMethod = _parseMethod;
		if (source != null) {
			parse();
		}
	}

	private void parse() throws UnsupportedEncodingException {
		try {
		if (parseMethod == ParseMethod.SimpleHtmlExtracter) {
			bufferedReader = new BufferedReader(new CharArrayReader(this
					.getSource().toCharArray()));
			SimpleHtmlExtractor sh = new SimpleHtmlExtractor();
			sh.setReader(bufferedReader);
			sh.extract();
			title = sh.getTitle();
			content = sh.getContent();
		} else if (parseMethod == ParseMethod.BlockExtractor) {
			bufferedReader = new BufferedReader(new CharArrayReader(this
					.getSource().toCharArray()));
			BlockExtractor be = new BlockExtractor();
			be.setReader(bufferedReader);
			be.extract();
			title = be.getTitle();
			content = be.getContent();
		}
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public void GetHtmlFileFromNet(String _hostName, String _get)
			throws ClientProtocolException, IOException {
		// TODO Auto-generated constructor stub
		netFilePath = _get;
		hostName = _hostName;

		HttpHost targetHost = new HttpHost(hostName);

		// init request
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(netFilePath);

		// preset header
		httpget.setHeader("User-Agent",
				"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.1.2)");
		httpget.setHeader("Accept-Language", "zh-cn,zh;q=0.5");
		httpget.setHeader("Accept-Charset", "GB2312,utf-8;q=0.7,*;q=0.7");

		// request and get response
		HttpResponse response = httpclient.execute(targetHost, httpget);

		// check and re-send request if needed
		int statusCode = response.getStatusLine().getStatusCode();
		if ((statusCode == HttpStatus.SC_MOVED_PERMANENTLY)
				|| (statusCode == HttpStatus.SC_MOVED_TEMPORARILY)
				|| (statusCode == HttpStatus.SC_SEE_OTHER)
				|| (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT)) {

			String newUri = response.getLastHeader("Location").getValue();
			httpclient = new DefaultHttpClient();
			httpget = new HttpGet(newUri);
			response = httpclient.execute(httpget);
		}

		// Get hold of the response entity
		HttpEntity entity = response.getEntity();
		// If the response does not enclose an entity, there is no need
		// to bother about connection release
		if (entity != null) {
			bytes = EntityUtils.toByteArray(entity);
			// String s = new String(bytes);
			charSet = EntityUtils.getContentCharSet(entity);

			if (charSet == null || "".equals(charSet.trim())) {
				// set rule
				String regEx = "(?=<meta).*?(?<=charset=[\\'|\\\"]?)([[a-z]|[A-Z]|[0-9]|-]*)";
				// get pattern
				Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
				// to match
				Matcher m = p.matcher(new String(bytes));

				if (m.find()) {
					// System.out.println("start(): "+m.start());
					// System.out.println("end(): "+m.end());
					charSet = m.group(1);
				} else {
					charSet = "utf-8";
				}
			}
		}

		httpclient.getConnectionManager().shutdown();
		source = new String(bytes, charSet);
		bufferedReader = new BufferedReader(new CharArrayReader(this
				.getSource().toCharArray()));
		parse();
		// this.setReader(this.getBudferedReader());
		// this.extract();
	}

	public void GetHtmlFileFromLocalFile(String _localPath, String _charSet)
			throws Exception {
		if (_charSet != null) {
			charSet = _charSet;
		}
		if (_localPath != null) {
			filePath = _localPath;
		}
		FileInputStream fis = new FileInputStream(filePath);
		int size = fis.available();
		if (size > 64 * 1024 * 1024)// set max HTML size as 64Mb
		{
			throw new Exception("The file " + filePath + "is too large");
		} else {
			bytes = new byte[size];
			fis.read(bytes);
		}
		source = new String(bytes, charSet);
		bufferedReader = new BufferedReader(new CharArrayReader(this
				.getSource().toCharArray()));
		parse();
		// this.setReader(this.getBudferedReader());
		// this.extract();
	}

	public void SaveAs(String filename) throws IOException {
		// open output stream
		java.io.FileOutputStream out = new java.io.FileOutputStream(filename);
		java.io.BufferedWriter bw = new java.io.BufferedWriter(
				new java.io.OutputStreamWriter(out, charSet));

		ByteToCharConverter converter = ByteToCharConverter
				.getConverter(charSet);
		char chars[] = converter.convertAll(bytes);
		bw.write(chars, 0, chars.length);
		bw.flush();
		bw.close();
	}

	public String getSource() {
		return source;
	}

	public String getContent() {
		// TODO Auto-generated method stub
		return content;
	}

	public String getContent(ParseMethod _parseMethod) {
		// TODO Auto-generated method stub
		if (_parseMethod == ParseMethod.BlockExtractor) {
			try {
				bufferedReader = new BufferedReader(new CharArrayReader(this
						.getSource().toCharArray()));
				BlockExtractor be = new BlockExtractor();
				be.setReader(bufferedReader);
				be.extract();
				title = be.getTitle();
				return be.getContent();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace();
				return null;
			}
		} else {
			return content;
		}
	}

	public String getTitle() {
		// TODO Auto-generated method stub
		return title;
	}
}
