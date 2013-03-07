//import java.sql.Connection;
//import java.sql.SQLException;
//
//import junit.framework.TestCase;
//
//import org.junit.Test;
//
//import edu.bit.dlde.database.DLDEDatabasePool;
//
//
//public class TestDataBasePool extends TestCase{
//	
//	@Test
//	public void testPool(){
//		DLDEDatabasePool pool=DLDEDatabasePool.getInstance();
//		Connection con=pool.getConnection();
//		try {
//			con.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		pool.close();
//	}
//
//}
