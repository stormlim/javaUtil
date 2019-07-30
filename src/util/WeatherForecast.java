package util;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.alibaba.fastjson.JSONObject;

public class WeatherForecast {
	
	public static void main(String[] args) {
		//获取屏幕宽高
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight();
        // 创建 JFrame 实例
        JFrame frame = new JFrame("天气预报");
        // Setting the width and height of frame
        frame.setSize(600, 300);
        frame.setResizable(false);//禁止缩放
        frame.setIconImage(frame.getToolkit().getImage(System.getProperty("user.dir")+"/source/images/icon.jpg"));
        frame.setLocation(screenWidth/2-300, screenHeight/2-150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        /* 创建面板，这个类似于 HTML 的 div 标签
         * 我们可以创建多个面板并在 JFrame 中指定位置
         * 面板中我们可以添加文本字段，按钮及其他组件。
         */
        NewPanel panel = new NewPanel();    
        // 添加面板
        frame.add(panel);
        if(readXmlData()==null||readXmlData().equals("")) {
	        //城市设置界面
        	SetCityPanel(panel,frame);
        }else
        	MainPanel(panel,frame);
        // 设置界面可见
        frame.setVisible(true);
        while(true) {
        	try {
				TimeUnit.SECONDS.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        	if(panel.findComponentAt(40, 60).getWidth()==120) {
        		MainPanel(panel, frame);
        	}
        }
    }
	
	private static void MainPanel(JPanel panel, Frame frame) {
		String cityName = readXmlData();
		JSONObject json = WeatherData.CityWeather(cityName);
		String status = json.getJSONArray("HeWeather5").getJSONObject(0).getString("status");
        if(status.equals("unknown location")||status.equals("invalid param")) {
        	writeXmlData("");
        	JLabel warnLabel = new JLabel("提示： 无该城市数据，请重新输入");
        	warnLabel.setFont(new Font("微软雅黑",Font.PLAIN, 12));
        	warnLabel.setBounds(150,130,200,25);
            panel.add(warnLabel);
            panel.repaint();
        	return ;
        }else {
        	panel.setLayout(null);//这边设置布局为 null
    		panel.setBackground(Color.CYAN);
    		panel.removeAll();
        }
		
        JLabel locationLabel = new JLabel("城市 : " + readXmlData());
        locationLabel.setFont(new Font("微软雅黑",Font.PLAIN, 12));
        locationLabel.setBounds(10,5,60,25);
        panel.add(locationLabel);
        JLabel changeLabel = new JLabel(" 切换");
        changeLabel.setBorder(BorderFactory.createLineBorder(Color.RED, 1));
        changeLabel.setFont(new Font("微软雅黑",Font.PLAIN, 11));
        changeLabel.setBounds(80,10,30,15);
        changeLabel.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e){
                SetCityPanel(panel, frame);
            }
        	public void mouseEntered(MouseEvent e) {
        		changeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        	}
		});
        panel.add(changeLabel);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        JLabel dateLabel = new JLabel("更新时间：" + dateFormat.format(new Date()));
        dateLabel.setFont(new Font("微软雅黑",Font.PLAIN, 11));
        dateLabel.setBounds(490,5,100,25);
        panel.add(dateLabel);
        
        //实时天气
        JLabel titleLabel1 = new JLabel("实时天气");
        titleLabel1.setFont(new Font("微软雅黑",Font.BOLD, 14));
        titleLabel1.setBounds(40,60,120,25);
        panel.add(titleLabel1);
        JLabel weatherLabel1 = new JLabel("天气 : " + json.getJSONArray("HeWeather5").getJSONObject(0).getJSONObject("now").getJSONObject("cond").getString("txt"));
        weatherLabel1.setFont(new Font("微软雅黑",Font.PLAIN, 13));
        weatherLabel1.setBounds(40,95,120,25);
        panel.add(weatherLabel1);
        JLabel tempLabel1 = new JLabel("温度 : " + json.getJSONArray("HeWeather5").getJSONObject(0).getJSONObject("now").getString("tmp")+" ℃");
        tempLabel1.setFont(new Font("微软雅黑",Font.PLAIN, 13));
        tempLabel1.setBounds(40,130,120,25);
        panel.add(tempLabel1);
        JLabel windLabel1 = new JLabel("风向 : " + json.getJSONArray("HeWeather5").getJSONObject(0).getJSONObject("now").getJSONObject("wind").getString("dir"));
        windLabel1.setFont(new Font("微软雅黑",Font.PLAIN, 13));
        windLabel1.setBounds(40,165,120,25);
        panel.add(windLabel1);
        JLabel windNumLabel1 = new JLabel("风力 : " + json.getJSONArray("HeWeather5").getJSONObject(0).getJSONObject("now").getJSONObject("wind").getString("sc")+" 级");
        windNumLabel1.setFont(new Font("微软雅黑",Font.PLAIN, 13));
        windNumLabel1.setBounds(40,200,120,25);
        panel.add(windNumLabel1);
        //今日天气
        JLabel titleLabel2 = new JLabel("今日");
        titleLabel2.setFont(new Font("微软雅黑",Font.BOLD, 14));
        titleLabel2.setBounds(180,60,120,25);
        panel.add(titleLabel2);
        JLabel weatherLabel2 = new JLabel("天气 : " + json.getJSONArray("HeWeather5").getJSONObject(0).getJSONArray("daily_forecast").getJSONObject(0).getJSONObject("cond").getString("txt_n"));
        weatherLabel2.setFont(new Font("微软雅黑",Font.PLAIN, 13));
        weatherLabel2.setBounds(180,95,120,25);
        panel.add(weatherLabel2);
        JLabel tempLabel2 = new JLabel("温度 : " + json.getJSONArray("HeWeather5").getJSONObject(0).getJSONArray("daily_forecast").getJSONObject(0).getJSONObject("tmp").getString("min")+"-"+json.getJSONArray("HeWeather5").getJSONObject(0).getJSONArray("daily_forecast").getJSONObject(0).getJSONObject("tmp").getString("max")+" ℃");
        tempLabel2.setFont(new Font("微软雅黑",Font.PLAIN, 13));
        tempLabel2.setBounds(180,130,120,25);
        panel.add(tempLabel2);
        JLabel windLabel2 = new JLabel("风向 : " + json.getJSONArray("HeWeather5").getJSONObject(0).getJSONArray("daily_forecast").getJSONObject(0).getJSONObject("wind").getString("dir"));
        windLabel2.setFont(new Font("微软雅黑",Font.PLAIN, 13));
        windLabel2.setBounds(180,165,120,25);
        panel.add(windLabel2);
        JLabel windNumLabel2 = new JLabel("风力 : " + json.getJSONArray("HeWeather5").getJSONObject(0).getJSONArray("daily_forecast").getJSONObject(0).getJSONObject("wind").getString("sc") + " 级");
        windNumLabel2.setFont(new Font("微软雅黑",Font.PLAIN, 13));
        windNumLabel2.setBounds(180,200,120,25);
        panel.add(windNumLabel2);
        //明日天气
        JLabel titleLabel3 = new JLabel("明日");
        titleLabel3.setFont(new Font("微软雅黑",Font.BOLD, 14));
        titleLabel3.setBounds(320,60,120,25);
        panel.add(titleLabel3);
        JLabel weatherLabel3 = new JLabel("天气 : " + json.getJSONArray("HeWeather5").getJSONObject(0).getJSONArray("daily_forecast").getJSONObject(1).getJSONObject("cond").getString("txt_n"));
        weatherLabel3.setFont(new Font("微软雅黑",Font.PLAIN, 13));
        weatherLabel3.setBounds(320,95,120,25);
        panel.add(weatherLabel3);
        JLabel tempLabel3 = new JLabel("温度 : " + json.getJSONArray("HeWeather5").getJSONObject(0).getJSONArray("daily_forecast").getJSONObject(1).getJSONObject("tmp").getString("min")+"-"+json.getJSONArray("HeWeather5").getJSONObject(0).getJSONArray("daily_forecast").getJSONObject(0).getJSONObject("tmp").getString("max")+" ℃");
        tempLabel3.setFont(new Font("微软雅黑",Font.PLAIN, 13));
        tempLabel3.setBounds(320,130,120,25);
        panel.add(tempLabel3);
        JLabel windLabel3 = new JLabel("风向 : " + json.getJSONArray("HeWeather5").getJSONObject(0).getJSONArray("daily_forecast").getJSONObject(1).getJSONObject("wind").getString("dir"));
        windLabel3.setFont(new Font("微软雅黑",Font.PLAIN, 13));
        windLabel3.setBounds(320,165,120,25);
        panel.add(windLabel3);
        JLabel windNumLabel3 = new JLabel("风力 : " + json.getJSONArray("HeWeather5").getJSONObject(0).getJSONArray("daily_forecast").getJSONObject(1).getJSONObject("wind").getString("sc") + " 级");
        windNumLabel3.setFont(new Font("微软雅黑",Font.PLAIN, 13));
        windNumLabel3.setBounds(320,200,120,25);
        panel.add(windNumLabel3);
        //后日天气
        JLabel titleLabel4 = new JLabel("后日");
        titleLabel4.setFont(new Font("微软雅黑",Font.BOLD, 14));
        titleLabel4.setBounds(460,60,120,25);
        panel.add(titleLabel4);
        JLabel weatherLabel4 = new JLabel("天气 : " + json.getJSONArray("HeWeather5").getJSONObject(0).getJSONArray("daily_forecast").getJSONObject(2).getJSONObject("cond").getString("txt_n"));
        weatherLabel4.setFont(new Font("微软雅黑",Font.PLAIN, 13));
        weatherLabel4.setBounds(460,95,120,25);
        panel.add(weatherLabel4);
        JLabel tempLabel4 = new JLabel("温度 : " + json.getJSONArray("HeWeather5").getJSONObject(0).getJSONArray("daily_forecast").getJSONObject(2).getJSONObject("tmp").getString("min")+"-"+json.getJSONArray("HeWeather5").getJSONObject(0).getJSONArray("daily_forecast").getJSONObject(0).getJSONObject("tmp").getString("max")+" ℃");
        tempLabel4.setFont(new Font("微软雅黑",Font.PLAIN, 13));
        tempLabel4.setBounds(460,130,120,25);
        panel.add(tempLabel4);
        JLabel windLabel4 = new JLabel("风向 : " + json.getJSONArray("HeWeather5").getJSONObject(0).getJSONArray("daily_forecast").getJSONObject(2).getJSONObject("wind").getString("dir"));
        windLabel4.setFont(new Font("微软雅黑",Font.PLAIN, 13));
        windLabel4.setBounds(460,165,120,25);
        panel.add(windLabel4);
        JLabel windNumLabel4 = new JLabel("风力 : " + json.getJSONArray("HeWeather5").getJSONObject(0).getJSONArray("daily_forecast").getJSONObject(2).getJSONObject("wind").getString("sc") + " 级");
        windNumLabel4.setFont(new Font("微软雅黑",Font.PLAIN, 13));
        windNumLabel4.setBounds(460,200,120,25);
        panel.add(windNumLabel4);
        frame.repaint();//重新绘制frame
    }

    private static void SetCityPanel(JPanel panel,Frame frame) {
    	
        panel.setLayout(null);//这边设置布局为 null
        panel.setBackground(Color.CYAN);
        panel.removeAll();
        // 创建 JLabel
        JLabel locationLabel = new JLabel("城市:");
        locationLabel.setBounds(150,100,40,25);//x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
        panel.add(locationLabel);

        /* 
         * 创建文本域用于用户输入
         */
        JTextField locationText = new JTextField(20);
        locationText.setBounds(190,100,160,25);
        panel.add(locationText);

        // 创建按钮
        JButton loginButton = new JButton("保存");
        loginButton.setBounds(360, 100, 80, 25);
        // 添加按钮的点击事件监听器
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                writeXmlData(locationText.getText());
                MainPanel(panel,frame);
            }
        });
        panel.add(loginButton);
        frame.repaint();
    }
    
    public static String readXmlData() {
    	File file = new File("D:/WeatherForecast.xml");
    	if(!file.exists()) {
    		FileWriter writer = null;
			try {
				writer = new FileWriter(file);
				writer.write("<city></city>");
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
    	}
    	try{
            SAXReader reader=new SAXReader();
            Document doc=reader.read(file);
            Element rootElement=doc.getRootElement();
            return rootElement.getText();
        }catch (Exception e1) {
            e1.printStackTrace();
	    }
    	return null;
    }
    
    public static void writeXmlData(String city) {
    	File file = new File("D:/WeatherForecast.xml");
    	try{
            SAXReader reader=new SAXReader();
            Document doc=reader.read(file);
            Element rootElement=doc.getRootElement();
            rootElement.setText(city);
            //指定文件输出的位置
            FileOutputStream out =new FileOutputStream("D:/WeatherForecast.xml");
            // 指定文本的写出的格式：
            OutputFormat format=OutputFormat.createPrettyPrint();
            format.setEncoding("UTF-8");
            //1.创建写出对象
            XMLWriter writer=new XMLWriter(out,format);
            //2.写出Document对象
            writer.write(doc);
            //3.关闭流
            writer.close();
        }catch (Exception e1) {
            e1.printStackTrace();
	    }
    }
}

class NewPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	ImageIcon icon;
	Image img;
	public NewPanel() {
		icon = new ImageIcon(System.getProperty("user.dir")+"/source/images/bg.png");
		img = icon.getImage();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0,this.getWidth(), this.getHeight(), this);
	}
 
}
