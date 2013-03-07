/**
 * 
 */
package mycomponents;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * 使用GridBagConstraints进行布局的自定义Panel
 * @author hz
 */
@SuppressWarnings("serial")
public class GBLPanel extends JPanel {
	private GridBagConstraints gbc = null;

	public static enum GBCFill {
		HORIZONTAL, VERTICAL, BOTH, NONE
	};

	public static enum GBCAnchor {
		//
		ABOVE_BASELINE, ABOVE_BASELINE_LEADING, ABOVE_BASELINE_TRAILING, BASELINE, BASELINE_LEADING, BASELINE_TRAILING, BELOW_BASELINE, BELOW_BASELINE_LEADING, BELOW_BASELINE_TRAILING,
		//
		CENTER, NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST,
		//
		PAGE_START, PAGE_END, LINE_START, LINE_END, FIRST_LINE_START, FIRST_LINE_END, LAST_LINE_START, LAST_LINE_END
	}

	/**
	 * @param border 设置GBLPanel的border类型
	 */
	public GBLPanel(Border border) {
		gbc = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		if (border != null) {
			this.setBorder(border);
		}
		// TODO Auto-generated constructor stub
	}
	public GBLPanel() {
		gbc = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param insets ָ此字段指定组件的外部填充，即组件与其显示区域边缘之间间距的最小量。
	 * @param ipadx 此字段指定组件的内部填充，即给组件的最小宽度添加多大的空间。
	 * @param ipady 此字段指定内部填充，即给组件的最小高度添加多大的空间。
	 */
	public void setGBC(Insets insets, int ipadx, int ipady) {
		if (insets != null) {
			gbc.insets = insets;
		}
		if (ipadx != -1) {
			gbc.ipadx = ipadx;
		}
		if (ipady != -1) {
			gbc.ipady = ipady;
		}
	}

	/**
	 * 向该GBLPanel中添加一个组件
	 * @param com 需要添加的组件
	 * @param preferredSize 初始大小
	 * @param _x 指定包含组件的显示区域开始边的单元格，其中行的第一个单元格为 _x=0。
	 * @param _y 指定位于组件显示区域的顶部的单元格，其中最上边的单元格为 _y=0。
	 * @param _w 指定组件显示区域的某一行中的单元格数
	 * @param _h 指定在组件显示区域的一列中的单元格数。
	 * @param _wx 指定如何分布额外的水平空间。
	 * @param _wy 指定如何分布额外的垂直空间。
	 * @param fill 当组件的显示区域大于它所请求的显示区域的大小时使用此字段:它可以确定是否调整组件大小，以及在需要的时候如何进行调整。
	 * 
	 * 	以下值适用于 fill：
	 * 	
	 * 	NONE：不调整组件大小。
	 * 	HORIZONTAL：加宽组件，使它在水平方向上填满其显示区域，但是不改变高度。
	 * 	VERTICAL：加高组件，使它在垂直方向上填满其显示区域，但是不改变宽度。
	 * 	BOTH：使组件完全填满其显示区域。
	 * 	默认值为 NONE。
	 * @param anchor 当组件小于其显示区域时使用此字段。它可以确定在显示区域中放置组件的位置。
	 * 可能的值有三种：相对于方向的值、相对于基线的值和绝对值。相对于方向的值是相对于容器的组件方向属性进行解释的，
	 * 相对于基线值是相对于基线进行解释的，绝对值则不然。绝对值有：CENTER、NORTH、NORTHEAST、EAST、SOUTHEAST、SOUTH、SOUTHWEST、WEST 
	 * 和 NORTHWEST。方向相对值有：PAGE_START、PAGE_END、LINE_START、LINE_END、FIRST_LINE_START、FIRST_LINE_END、LAST_LINE_START 
	 * 和 LAST_LINE_END。相对于基线的值有：BASELINE、BASELINE_LEADING、BASELINE_TRAILING、ABOVE_BASELINE、
	 * ABOVE_BASELINE_LEADING、ABOVE_BASELINE_TRAILING、BELOW_BASELINE、BELOW_BASELINE_LEADING 和 BELOW_BASELINE_TRAILING。
	 * 默认值为 CENTER。
	 */
	public void addAComponent(Component com, Dimension preferredSize, int _x,
			int _y, int _w, int _h, double _wx, double _wy, GBCFill fill,
			GBCAnchor anchor) {
		switch (fill) {
			case HORIZONTAL:
				gbc.fill = GridBagConstraints.HORIZONTAL;
				break;
			case VERTICAL:
				gbc.fill = GridBagConstraints.VERTICAL;
				break;
			case BOTH:
				gbc.fill = GridBagConstraints.BOTH;
				break;
			case NONE:
				gbc.fill = GridBagConstraints.NONE;
				break;
			default:
				gbc.fill = GridBagConstraints.NONE;
		}

		switch (anchor) {
			case ABOVE_BASELINE:
				gbc.anchor = GridBagConstraints.ABOVE_BASELINE;
				break;
			case ABOVE_BASELINE_LEADING:
				gbc.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
				break;
			case ABOVE_BASELINE_TRAILING:
				gbc.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
				break;
			case BASELINE:
				gbc.anchor = GridBagConstraints.BASELINE;
				break;
			case BASELINE_LEADING:
				gbc.anchor = GridBagConstraints.BASELINE_LEADING;
				break;
			case BASELINE_TRAILING:
				gbc.anchor = GridBagConstraints.BASELINE_TRAILING;
				break;
			case BELOW_BASELINE:
				gbc.anchor = GridBagConstraints.BELOW_BASELINE;
				break;
			case BELOW_BASELINE_LEADING:
				gbc.anchor = GridBagConstraints.BELOW_BASELINE_LEADING;
				break;
			case BELOW_BASELINE_TRAILING:
				gbc.anchor = GridBagConstraints.BELOW_BASELINE_TRAILING;
				break;
			//
			case CENTER:
				gbc.anchor = GridBagConstraints.CENTER;
				break;
			case NORTH:
				gbc.anchor = GridBagConstraints.NORTH;
				break;
			case NORTHEAST:
				gbc.anchor = GridBagConstraints.NORTHEAST;
				break;
			case EAST:
				gbc.anchor = GridBagConstraints.EAST;
				break;
			case SOUTHEAST:
				gbc.anchor = GridBagConstraints.SOUTHEAST;
				break;
			case SOUTH:
				gbc.anchor = GridBagConstraints.SOUTH;
				break;
			case SOUTHWEST:
				gbc.anchor = GridBagConstraints.SOUTHWEST;
				break;
			case WEST:
				gbc.anchor = GridBagConstraints.WEST;
				break;
			case NORTHWEST:
				gbc.anchor = GridBagConstraints.NORTHWEST;
				break;
			//
			case PAGE_START:
				gbc.anchor = GridBagConstraints.PAGE_START;
				break;
			case PAGE_END:
				gbc.anchor = GridBagConstraints.PAGE_END;
				break;
			case LINE_START:
				gbc.anchor = GridBagConstraints.LINE_START;
				break;
			case LINE_END:
				gbc.anchor = GridBagConstraints.LINE_END;
				break;
			case FIRST_LINE_START:
				gbc.anchor = GridBagConstraints.FIRST_LINE_START;
				break;
			case FIRST_LINE_END:
				gbc.anchor = GridBagConstraints.FIRST_LINE_END;
				break;
			case LAST_LINE_START:
				gbc.anchor = GridBagConstraints.LAST_LINE_START;
				break;
			case LAST_LINE_END:
				gbc.anchor = GridBagConstraints.LAST_LINE_END;
				break;
			default:
				gbc.anchor = GridBagConstraints.CENTER;

		}
		gbc.gridx = _x;
		gbc.gridy = _y;
		gbc.gridwidth = _w;
		gbc.gridheight = _h;
		gbc.weightx = _wx;
		gbc.weighty = _wy;
		if (preferredSize != null) {
			com.setPreferredSize(preferredSize);
		}
		this.add(com, gbc);
	}
	
	private static GridBagConstraints sGBC = null;
	/**
	 * 设置panel的border类型，并转换为GBLPanel，之后调用static addAComponent进行详细参数设置
	*/
	public static JPanel SetToGBLPanel(JPanel panel,Border border)
	{
		sGBC= new GridBagConstraints();
		panel.setLayout(new GridBagLayout());
		if (border != null) {
			panel.setBorder(border);
		}
		return panel;
	}
	/**
	 * 将container转换为GBLPanel，之后调用static addAComponent进行详细参数设置
	*/
	public static Container SetToGBLPanel(Container container,Border border)
	{
		sGBC= new GridBagConstraints();
		container.setLayout(new GridBagLayout());
		return container;
	}
	/**
	 * 设置填充，此为静态函数
	 * @param insets ָ此字段指定组件的外部填充，即组件与其显示区域边缘之间间距的最小量。
	 * @param ipadx 此字段指定组件的内部填充，即给组件的最小宽度添加多大的空间。
	 * @param ipady 此字段指定内部填充，即给组件的最小高度添加多大的空间。
	 */
	public static void sSetGBC(Insets insets, int ipadx, int ipady) {
		if (insets != null) {
			sGBC.insets = insets;
		}
		if (ipadx != -1) {
			sGBC.ipadx = ipadx;
		}
		if (ipady != -1) {
			sGBC.ipady = ipady;
		}
	}
	/**
	 * 向一个特定容器中添加一个组件，此为静态方法
	 * @param con 需要添加组件的容器
	 * @param com 需要添加的组件
	 * @param preferredSize 初始大小
	 * @param _x 指定包含组件的显示区域开始边的单元格，其中行的第一个单元格为 _x=0。
	 * @param _y 指定位于组件显示区域的顶部的单元格，其中最上边的单元格为 _y=0。
	 * @param _w 指定组件显示区域的某一行中的单元格数
	 * @param _h 指定在组件显示区域的一列中的单元格数。
	 * @param _wx 指定如何分布额外的水平空间。
	 * @param _wy 指定如何分布额外的垂直空间。
	 * @param fill 当组件的显示区域大于它所请求的显示区域的大小时使用此字段:它可以确定是否调整组件大小，以及在需要的时候如何进行调整。
	 * 
	 * 	以下值适用于 fill：
	 * 	
	 * 	NONE：不调整组件大小。
	 * 	HORIZONTAL：加宽组件，使它在水平方向上填满其显示区域，但是不改变高度。
	 * 	VERTICAL：加高组件，使它在垂直方向上填满其显示区域，但是不改变宽度。
	 * 	BOTH：使组件完全填满其显示区域。
	 * 	默认值为 NONE。
	 * @param anchor 当组件小于其显示区域时使用此字段。它可以确定在显示区域中放置组件的位置。
	 * 可能的值有三种：相对于方向的值、相对于基线的值和绝对值。相对于方向的值是相对于容器的组件方向属性进行解释的，
	 * 相对于基线值是相对于基线进行解释的，绝对值则不然。绝对值有：CENTER、NORTH、NORTHEAST、EAST、SOUTHEAST、SOUTH、SOUTHWEST、WEST 
	 * 和 NORTHWEST。方向相对值有：PAGE_START、PAGE_END、LINE_START、LINE_END、FIRST_LINE_START、FIRST_LINE_END、LAST_LINE_START 
	 * 和 LAST_LINE_END。相对于基线的值有：BASELINE、BASELINE_LEADING、BASELINE_TRAILING、ABOVE_BASELINE、
	 * ABOVE_BASELINE_LEADING、ABOVE_BASELINE_TRAILING、BELOW_BASELINE、BELOW_BASELINE_LEADING 和 BELOW_BASELINE_TRAILING。
	 * 默认值为 CENTER。
	*/
	public static void addAComponent(Container con,Component com, Dimension preferredSize, int _x,
			int _y, int _w, int _h, double _wx, double _wy, GBCFill fill,
			GBCAnchor anchor) {
		switch (fill) {
		case HORIZONTAL:
			sGBC.fill = GridBagConstraints.HORIZONTAL;
			break;
		case VERTICAL:
			sGBC.fill = GridBagConstraints.VERTICAL;
			break;
		case BOTH:
			sGBC.fill = GridBagConstraints.BOTH;
			break;
		case NONE:
			sGBC.fill = GridBagConstraints.NONE;
			break;
		default:
			sGBC.fill = GridBagConstraints.NONE;
		}

		switch (anchor) {
		case ABOVE_BASELINE:
			sGBC.anchor = GridBagConstraints.ABOVE_BASELINE;
			break;
		case ABOVE_BASELINE_LEADING:
			sGBC.anchor = GridBagConstraints.ABOVE_BASELINE_LEADING;
			break;
		case ABOVE_BASELINE_TRAILING:
			sGBC.anchor = GridBagConstraints.ABOVE_BASELINE_TRAILING;
			break;
		case BASELINE:
			sGBC.anchor = GridBagConstraints.BASELINE;
			break;
		case BASELINE_LEADING:
			sGBC.anchor = GridBagConstraints.BASELINE_LEADING;
			break;
		case BASELINE_TRAILING:
			sGBC.anchor = GridBagConstraints.BASELINE_TRAILING;
			break;
		case BELOW_BASELINE:
			sGBC.anchor = GridBagConstraints.BELOW_BASELINE;
			break;
		case BELOW_BASELINE_LEADING:
			sGBC.anchor = GridBagConstraints.BELOW_BASELINE_LEADING;
			break;
		case BELOW_BASELINE_TRAILING:
			sGBC.anchor = GridBagConstraints.BELOW_BASELINE_TRAILING;
			break;
		//
		case CENTER:
			sGBC.anchor = GridBagConstraints.CENTER;
			break;
		case NORTH:
			sGBC.anchor = GridBagConstraints.NORTH;
			break;
		case NORTHEAST:
			sGBC.anchor = GridBagConstraints.NORTHEAST;
			break;
		case EAST:
			sGBC.anchor = GridBagConstraints.EAST;
			break;
		case SOUTHEAST:
			sGBC.anchor = GridBagConstraints.SOUTHEAST;
			break;
		case SOUTH:
			sGBC.anchor = GridBagConstraints.SOUTH;
			break;
		case SOUTHWEST:
			sGBC.anchor = GridBagConstraints.SOUTHWEST;
			break;
		case WEST:
			sGBC.anchor = GridBagConstraints.WEST;
			break;
		case NORTHWEST:
			sGBC.anchor = GridBagConstraints.NORTHWEST;
			break;
		//
		case PAGE_START:
			sGBC.anchor = GridBagConstraints.PAGE_START;
			break;
		case PAGE_END:
			sGBC.anchor = GridBagConstraints.PAGE_END;
			break;
		case LINE_START:
			sGBC.anchor = GridBagConstraints.LINE_START;
			break;
		case LINE_END:
			sGBC.anchor = GridBagConstraints.LINE_END;
			break;
		case FIRST_LINE_START:
			sGBC.anchor = GridBagConstraints.FIRST_LINE_START;
			break;
		case FIRST_LINE_END:
			sGBC.anchor = GridBagConstraints.FIRST_LINE_END;
			break;
		case LAST_LINE_START:
			sGBC.anchor = GridBagConstraints.LAST_LINE_START;
			break;
		case LAST_LINE_END:
			sGBC.anchor = GridBagConstraints.LAST_LINE_END;
			break;
		default:
			sGBC.anchor = GridBagConstraints.CENTER;

		}
		sGBC.gridx = _x;
		sGBC.gridy = _y;
		sGBC.gridwidth = _w;
		sGBC.gridheight = _h;
		sGBC.weightx = _wx;
		sGBC.weighty = _wy;
		if (preferredSize != null) {
			com.setPreferredSize(preferredSize);
		}
		con.add(com, sGBC);
	}
	

}
