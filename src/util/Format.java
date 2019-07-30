package util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Document.OutputSettings;

public class Format {
	
	
    /**
     * 返回格式化JSON字符串。
     * 
     * @param json 未格式化的JSON字符串。
     * @return 格式化的JSON字符串。
     */
    public static String formatJson(String json)
    {
        StringBuffer result = new StringBuffer();
 
        int length = json.length();
        int number = 0;
        char key = 0;
        //遍历输入字符串。
        for (int i = 0; i < length; i++)
        {
            //1、获取当前字符。
            key = json.charAt(i);
 
            //2、如果当前字符是前方括号、前花括号做如下处理：
            if((key == '[') || (key == '{') )
            {
                //（1）打印：当前字符。
                result.append(key);
 
                //（2）前方括号、前花括号，的后面必须换行。打印：换行。
                result.append('\n');
 
                //（3）每出现一次前方括号、前花括号；缩进次数增加一次。打印：新行缩进。
                number++;
                StringBuffer spaces = new StringBuffer();
                for(int ii = 0; ii < number; ii++)
                {
                    spaces.append("   ");
                }
                result.append(spaces);
 
                //（4）进行下一次循环。
                continue;
            }
 
            //3、如果当前字符是后方括号、后花括号做如下处理：
            if((key == ']') || (key == '}') )
            {
                //（1）后方括号、后花括号，的前面必须换行。打印：换行。
                result.append('\n');
 
                //（2）每出现一次后方括号、后花括号；缩进次数减少一次。打印：缩进。
                number--;
                StringBuffer spaces = new StringBuffer();
                for(int ii = 0; ii < number; ii++)
                {
                    spaces.append("   ");
                }
                result.append(spaces);
 
                //（3）打印：当前字符。
                result.append(key);
 
                //（4）如果当前字符后面还有字符，并且字符不为“，”，打印：换行。
                if(((i + 1) < length) && (json.charAt(i + 1) != ','))
                {
                    result.append('\n');
                }
 
                //（5）继续下一次循环。
                continue;
            }
 
            //4、如果当前字符是逗号。逗号后面换行，并缩进，不改变缩进次数。
            if((key == ','))
            {
                result.append(key);
                result.append('\n');
                StringBuffer spaces = new StringBuffer();
                for(int ii = 0; ii < number; ii++)
                {
                    spaces.append("   ");
                }
                result.append(spaces);
                continue;
            }
 
            //5、打印：当前字符。
            result.append(key);
        }
 
        return result.toString();
    }
    
    /**
     * 格式化html字符串
     * @param html
     */
    public static void formatHtml(String html) {
    	Document doc = Jsoup.parse(html);
    	OutputSettings outputSettings = new OutputSettings();
    	outputSettings.indentAmount(2);//设置输出缩进为2个空格
    	doc.outputSettings(outputSettings);
		html = doc.html();
		System.out.println(html);
    }
    
    public static String formatXml(String str){
        SAXReader reader = new SAXReader();
        // 注释：创建一个串的字符输入流
        StringReader in = new StringReader(str);
        org.dom4j.Document doc = null;
		try {
			doc = (org.dom4j.Document) reader.read(in);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // 注释：创建输出格式
        OutputFormat formater = OutputFormat.createPrettyPrint();
        
        //获取xml的编码
        Pattern pattern = Pattern.compile("encoding=\"(.*?)\"\\?>");
        Matcher charset = pattern.matcher(str);
        charset.find();
        // 注释：设置xml的输出编码
        formater.setEncoding(charset.group(1));
        // 注释：创建输出(目标)
        StringWriter out = new StringWriter();
        // 注释：创建输出流
        XMLWriter writer = new XMLWriter(out, formater);
        // 注释：输出格式化的串到目标中，执行后。格式化后的串保存在out中。
        try {
			writer.write(doc);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(out.toString());
        // 注释：返回我们格式化后的结果
        return out.toString();
    }
 
    public static void main(String[] args) {
    	//json格式化
        //Format json = new Format();
        //String str = "{\"HeWeather5\":[{\"now\":{\"hum\":\"92\",\"vis\":\"3\",\"pres\":\"948\",\"pcpn\":\"0.0\",\"fl\":\"25\",\"tmp\":\"22\",\"cond\":{\"txt\":\"多云\",\"code\":\"101\"},\"wind\":{\"sc\":\"0\",\"spd\":\"1\",\"deg\":\"250\",\"dir\":\"西南风\"}},\"suggestion\":{\"uv\":{\"txt\":\"属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。\",\"brf\":\"中等\"},\"cw\":{\"txt\":\"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。\",\"brf\":\"较适宜\"},\"trav\":{\"txt\":\"天气较好，但丝毫不会影响您出行的心情。温度适宜又有微风相伴，适宜旅游。\",\"brf\":\"适宜\"},\"air\":{\"txt\":\"气象条件对空气污染物稀释、扩散和清除无明显影响，易感人群应适当减少室外活动时间。\",\"brf\":\"中\"},\"comf\":{\"txt\":\"白天天气晴好，您在这种天气条件下，会感觉早晚凉爽、舒适，午后偏热。\",\"brf\":\"较舒适\"},\"drsg\":{\"txt\":\"天气热，建议着短裙、短裤、短薄外套、T恤等夏季服装。\",\"brf\":\"热\"},\"sport\":{\"txt\":\"天气较好，较适宜进行各种运动，但因天气热，请适当减少运动时间，降低运动强度。\",\"brf\":\"较适宜\"},\"flu\":{\"txt\":\"各项气象条件适宜，无明显降温过程，发生感冒机率较低。\",\"brf\":\"少发\"}},\"aqi\":{\"city\":{\"no2\":\"39\",\"pm25\":\"53\",\"o3\":\"66\",\"qlty\":\"良\",\"so2\":\"8\",\"aqi\":\"73\",\"pm10\":\"70\",\"co\":\"1.1\"}},\"basic\":{\"city\":\"成都\",\"update\":{\"loc\":\"2019-05-25 11:27\",\"utc\":\"2019-05-25 03:27\"},\"lon\":\"104.06573486\",\"id\":\"CN101270101\",\"cnty\":\"中国\",\"lat\":\"30.65946198\"},\"daily_forecast\":[{\"date\":\"2019-05-25\",\"pop\":\"25\",\"hum\":\"63\",\"uv\":\"5\",\"vis\":\"19\",\"astro\":{\"ss\":\"19:58\",\"mr\":\"00:42\",\"ms\":\"11:28\",\"sr\":\"06:03\"},\"pres\":\"944\",\"pcpn\":\"0.0\",\"tmp\":{\"min\":\"19\",\"max\":\"29\"},\"cond\":{\"txt_n\":\"晴\",\"code_n\":\"100\",\"code_d\":\"101\",\"txt_d\":\"多云\"},\"wind\":{\"sc\":\"1-2\",\"spd\":\"4\",\"deg\":\"-1\",\"dir\":\"无持续风向\"}},{\"date\":\"2019-05-26\",\"pop\":\"4\",\"hum\":\"34\",\"uv\":\"12\",\"vis\":\"18\",\"astro\":{\"ss\":\"19:58\",\"mr\":\"01:20\",\"ms\":\"12:22\",\"sr\":\"06:03\"},\"pres\":\"950\",\"pcpn\":\"0.0\",\"tmp\":{\"min\":\"20\",\"max\":\"31\"},\"cond\":{\"txt_n\":\"晴\",\"code_n\":\"100\",\"code_d\":\"100\",\"txt_d\":\"晴\"},\"wind\":{\"sc\":\"1-2\",\"spd\":\"11\",\"deg\":\"-1\",\"dir\":\"无持续风向\"}},{\"date\":\"2019-05-27\",\"pop\":\"13\",\"hum\":\"56\",\"uv\":\"9\",\"vis\":\"20\",\"astro\":{\"ss\":\"19:59\",\"mr\":\"01:55\",\"ms\":\"13:16\",\"sr\":\"06:02\"},\"pres\":\"955\",\"pcpn\":\"0.0\",\"tmp\":{\"min\":\"16\",\"max\":\"28\"},\"cond\":{\"txt_n\":\"晴\",\"code_n\":\"100\",\"code_d\":\"100\",\"txt_d\":\"晴\"},\"wind\":{\"sc\":\"1-2\",\"spd\":\"10\",\"deg\":\"-1\",\"dir\":\"无持续风向\"}}],\"status\":\"ok\"}]}";
        //String result = json.formatJson(str);
        //System.out.println(result);
 
        //HTML格式化
        //formatHtml("<!DOCTYPE html><html><head><meta charset=\"utf-8\"><title>菜鸟教程(runoob.com)</title></head><body><h1>我的第一个标题</h1><p>我的第一个段落。</p></body></html>");
    
    	//xml字符串格式化
        //String str = "<?xml version=\"1.0\" encoding=\"GBK\"?><RequestData><HeadData><UserCode>sh1_admin</UserCode><UserName>sh1_admin</UserName><UserCompanyCode>3107</UserCompanyCode><UserCompanyName>上海分公司一部</UserCompanyName><RequestType>03</RequestType></HeadData><BodyData><ReportId>113100000033</ReportId><Insurant>a5rfg87</Insurant><NumberPlate>沪E78612</NumberPlate><EngineModel></EngineModel><CarVin></CarVin><AccidentDate>2011-02-25 15:07:00</AccidentDate><ReportDate>2011-02-25 15:07:00</ReportDate><Province>310000</Province><City>310100</City><District></District><AccidentPlace>1</AccidentPlace><AccidentLongitude></AccidentLongitude><AccidentLatitude></AccidentLatitude><SurveyLongitude></SurveyLongitude><SurveyLatitude></SurveyLatitude><SceneReportFlag></SceneReportFlag><Reporter></Reporter><ReporterTel></ReporterTel><SurveyPlace></SurveyPlace><OperatorId>3525</OperatorId><OperatorName>sh_admin</OperatorName><ReportDealId>30000800</ReportDealId><ReportDealName>江苏分公司</ReportDealName><CompanyName></CompanyName><CustomerTypeCode></CustomerTypeCode><ForcePolicyId>a5rfg87a5rfg87a5rfg87</ForcePolicyId><BizPolicyId></BizPolicyId><Index>0</Index><FieldName>5</FieldName></BodyData></RequestData>";
        //formatXml(str);

    }

}
