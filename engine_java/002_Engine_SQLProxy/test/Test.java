import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.cell.CObject;
import com.cell.CUtil;
import com.cell.j2se.CAppBridge;
import com.cell.j2se.CStorage;
import com.cell.sql.pool.SQLPool;
import com.cell.sql.pool.SQLTask;
import com.g2d.util.Tracker;
import com.g2d.util.Util;



public class Test extends JFrame implements ActionListener 
{

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) throws Exception
	{
//		MySQL

		System.out.println("usage:");
		System.out.println("Test [host:port] [dbname] [user] [password]");
		System.out.println("Test [host:port] [dbname] [user] [password] [pool_min_size] [pool_max_size] [sql_commands]");
		
		if (args!=null && args.length>0)
		{
			CObject.initSystem(new CStorage("sql_pool"), new CAppBridge(String.class.getClassLoader(), String.class));

			if (args.length == 4)
			{
				Test test1 = new Test(args[0], args[1], args[2], args[3]);
				test1.setVisible(true);
			}
			else if (args.length == 7)
			{
				Test test1 = new Test(args[0], args[1], args[2], args[3], 
						Integer.parseInt(args[4]),
						Integer.parseInt(args[5]),
						args[6]
				);
				test1.setVisible(true);
			}
		}
		
//		PostgreSQL
//		{
//			Test test2 = new Test(
//					new SQLPool("org.postgresql.Driver", "jdbc:postgresql://server-win2k3:5432/eatworld", "root", "root", 
//					"test" , 10, 30, 
//					"test", 10, 10, 30
//					));
//			test2.setTitle("jdbc:postgresql://server-win2k3:5432/eatworld poolsize(" + 30 + ")");
//			test2.setVisible(true);
//		}
		
	}
	
	
	
	SQLPool TestSQLPool;

	JTextArea SqlCommand;
	
	JButton ButtonGC;
	JButton ButtonTest;
   
	Vector<String>	Commands = new Vector<String>();
	
	public Test(String host, String db, String user, String pswd) throws Exception{
		this(host, db, user, pswd, 10, 30, 
				"SELECT * FROM eat_version WHERE version = 1;\n" +
				"UPDATE eat_version SET data='newdata' WHERE version = 1;");
	}
	
	public Test(String host, String db, String user, String pswd, int pool_min_size, int pool_max_size, String commands) throws Exception
	{
		TestSQLPool = new SQLPool(
				"com.mysql.jdbc.Driver", 
				"jdbc:mysql://" + host + "/" + db, user, pswd, 
				"mysql_test", pool_min_size, pool_max_size
				);

		this.setTitle("mysql:/"+host + "/" + db + " pool_max_size(" + pool_max_size + ")");
		this.setLayout(new BorderLayout());
		this.setSize(800, 600);

		this.add(new PaintCanvas(), BorderLayout.NORTH);

		SqlCommand = new JTextArea(commands);
		SqlCommand.setFont(new Font("Courier New", Font.BOLD, 18));
		this.add(new JScrollPane(SqlCommand), BorderLayout.CENTER);
		
		JPanel bpan = new JPanel();
		this.add(bpan, BorderLayout.SOUTH);
		{
			ButtonGC = new JButton("GC");
			ButtonGC.addActionListener(this);
			bpan.add(ButtonGC);

			ButtonTest = new JButton("Test");
			ButtonTest.addActionListener(this);
			bpan.add(ButtonTest);
		}

		this.validate();

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});

	}
   
	public void exit() {
		try {
			this.setVisible(false);
			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
   
	public void actionPerformed(ActionEvent e)
	{
		if (e.getSource() == ButtonGC) {
			System.out.println("gc");
			System.gc();
		} 
		else if (e.getSource() == ButtonTest)
		{
			synchronized (Commands) 
			{
				Commands.clear();
				String[] commands = SqlCommand.getText().split(";");
				for (String cmd : commands) {
					if (cmd.trim().length()>0) {
						Commands.add(cmd);
					}
				}
				
				new Thread(){
					@Override
					public void run() {
						for (int i=0; i<10000; i++){
							TestSQLPool.executeTask(new TestTask(CUtil.getRandom(Commands)));
						}
					}
				}.start();
			}
		}
	}
	
	class PaintCanvas extends Canvas implements Runnable
	{
		private static final long serialVersionUID = 1L;
		
	    Tracker trace_pool_task_count = new Tracker.SecondRateTracker(120);
	    Tracker trace_pool_exec_count = new Tracker.SecondRateTracker(120);
	    
		PaintCanvas() 
		{
			setSize(800, 300);
			Thread t = new Thread(this);
			t.start();
		}
		
	    public void run() 
	    {
	    	for (;;)
	    	{
	    		try {
	    			if (Test.this.isVisible())
	    			{
	    				trace_pool_task_count.record(TestSQLPool.getCommitedCount());
	    				trace_pool_exec_count.record(TestSQLPool.getExecutor().getCompletedTaskCount());
		    			this.repaint();
		    			
		    			Thread.sleep(1000);
	    			}
	    			else
	    			{
	    				Thread.sleep(2000);
	    			}
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
	    	}
	    	//System.out.println("paint service is down !");
	    }
	    
		public void paint(Graphics g) 
		{
			Util.drawHeapStatus(g, Color.GRAY, 10, 10, getWidth()-20, 40);
			
			int rx1 = 10;
			int rx2 = getWidth()/2;
			int ry = 80;
			int rw = getWidth()/2 - 20;
			int rh = 80;
			{
				g.setColor(Color.RED);
				g.drawString(
						" 已提交的任务=" + TestSQLPool.getCommitedCount() + 
						" 已完成的任务=" + TestSQLPool.getExecutor().getCompletedTaskCount() + 
						" 历史最高值=" + (int)trace_pool_exec_count.getHistoryMax() +
						" 队列=" + (int)TestSQLPool.getExecutor().getQueue().size() +
						"", 
						rx1, ry - 4);
				trace_pool_task_count.drawGrap(g, Color.BLACK, Color.RED, "每秒提交了多少任务 ",
						rx1, ry, rw, rh);
				trace_pool_exec_count.drawGrap(g, Color.BLACK, Color.RED, "每秒执行了多少任务 ",
						rx2, ry, rw, rh);
				ry += rh + 20;
			}
		}

	}
   
	static class TestTask extends SQLTask
	{
		static int count = 0;
		
		int id = count++;
		String sql ;
		public TestTask(String sql) {
			this.sql = sql;
		}
		
		@Override
		public void execute(Connection c) throws SQLException {
			Statement statement = c.createStatement();
			statement.execute(sql);
			statement.close();
		}
//		
//		@Override
//		public void queried(Statement statement, ResultSet result) throws SQLException {
////			System.out.println("queryed");
////			ResultSetMetaData md = result.getMetaData();
////			while (result.next()){
////				for (int i=0; i<md.getColumnCount(); i++) {
//////					System.out.println(result.getObject(i+1)+"\t | ");
////				}
////			}
//		}
//		
//		@Override
//		public void updated(Statement statement, int rowcount) throws SQLException {
////			System.out.println("updated rowcount="+rowcount);
//		}
		
//		@Override
//		public boolean equals(Object obj) {
//			if (obj instanceof TestTask) {
//				return id == ((TestTask) obj).id;
//			}
//			return super.equals(obj);
//		}
	}
	

	
	
	
	
	
}

