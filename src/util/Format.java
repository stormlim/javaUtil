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
     * ���ظ�ʽ��JSON�ַ�����
     * 
     * @param json δ��ʽ����JSON�ַ�����
     * @return ��ʽ����JSON�ַ�����
     */
    public static String formatJson(String json)
    {
        StringBuffer result = new StringBuffer();
 
        int length = json.length();
        int number = 0;
        char key = 0;
        //���������ַ�����
        for (int i = 0; i < length; i++)
        {
            //1����ȡ��ǰ�ַ���
            key = json.charAt(i);
 
            //2�������ǰ�ַ���ǰ�����š�ǰ�����������´���
            if((key == '[') || (key == '{') )
            {
                //��1����ӡ����ǰ�ַ���
                result.append(key);
 
                //��2��ǰ�����š�ǰ�����ţ��ĺ�����뻻�С���ӡ�����С�
                result.append('\n');
 
                //��3��ÿ����һ��ǰ�����š�ǰ�����ţ�������������һ�Ρ���ӡ������������
                number++;
                StringBuffer spaces = new StringBuffer();
                for(int ii = 0; ii < number; ii++)
                {
                    spaces.append("   ");
                }
                result.append(spaces);
 
                //��4��������һ��ѭ����
                continue;
            }
 
            //3�������ǰ�ַ��Ǻ����š������������´���
            if((key == ']') || (key == '}') )
            {
                //��1�������š������ţ���ǰ����뻻�С���ӡ�����С�
                result.append('\n');
 
                //��2��ÿ����һ�κ����š������ţ�������������һ�Ρ���ӡ��������
                number--;
                StringBuffer spaces = new StringBuffer();
                for(int ii = 0; ii < number; ii++)
                {
                    spaces.append("   ");
                }
                result.append(spaces);
 
                //��3����ӡ����ǰ�ַ���
                result.append(key);
 
                //��4�������ǰ�ַ����滹���ַ��������ַ���Ϊ����������ӡ�����С�
                if(((i + 1) < length) && (json.charAt(i + 1) != ','))
                {
                    result.append('\n');
                }
 
                //��5��������һ��ѭ����
                continue;
            }
 
            //4�������ǰ�ַ��Ƕ��š����ź��滻�У������������ı�����������
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
 
            //5����ӡ����ǰ�ַ���
            result.append(key);
        }
 
        return result.toString();
    }
    
    /**
     * ��ʽ��html�ַ���
     * @param html
     */
    public static void formatHtml(String html) {
    	Document doc = Jsoup.parse(html);
    	OutputSettings outputSettings = new OutputSettings();
    	outputSettings.indentAmount(2);//�����������Ϊ2���ո�
    	doc.outputSettings(outputSettings);
		html = doc.html();
		System.out.println(html);
    }
    
    public static String formatXml(String str){
        SAXReader reader = new SAXReader();
        // ע�ͣ�����һ�������ַ�������
        StringReader in = new StringReader(str);
        org.dom4j.Document doc = null;
		try {
			doc = (org.dom4j.Document) reader.read(in);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // ע�ͣ����������ʽ
        OutputFormat formater = OutputFormat.createPrettyPrint();
        
        //��ȡxml�ı���
        Pattern pattern = Pattern.compile("encoding=\"(.*?)\"\\?>");
        Matcher charset = pattern.matcher(str);
        charset.find();
        // ע�ͣ�����xml���������
        formater.setEncoding(charset.group(1));
        // ע�ͣ��������(Ŀ��)
        StringWriter out = new StringWriter();
        // ע�ͣ����������
        XMLWriter writer = new XMLWriter(out, formater);
        // ע�ͣ������ʽ���Ĵ���Ŀ���У�ִ�к󡣸�ʽ����Ĵ�������out�С�
        try {
			writer.write(doc);
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(out.toString());
        // ע�ͣ��������Ǹ�ʽ����Ľ��
        return out.toString();
    }
 
    public static void main(String[] args) {
    	//json��ʽ��
        //Format json = new Format();
        //String str = "{\"HeWeather5\":[{\"now\":{\"hum\":\"92\",\"vis\":\"3\",\"pres\":\"948\",\"pcpn\":\"0.0\",\"fl\":\"25\",\"tmp\":\"22\",\"cond\":{\"txt\":\"����\",\"code\":\"101\"},\"wind\":{\"sc\":\"0\",\"spd\":\"1\",\"deg\":\"250\",\"dir\":\"���Ϸ�\"}},\"suggestion\":{\"uv\":{\"txt\":\"���е�ǿ�������߷������������ʱ����Ϳ��SPF����15��PA+�ķ�ɹ����Ʒ����ñ�ӡ�̫������\",\"brf\":\"�е�\"},\"cw\":{\"txt\":\"������ϴ����δ��һ�����꣬������С����ϴһ�µ����������ܱ���һ�졣\",\"brf\":\"������\"},\"trav\":{\"txt\":\"�����Ϻã���˿������Ӱ�������е����顣�¶���������΢����飬�������Ρ�\",\"brf\":\"����\"},\"air\":{\"txt\":\"���������Կ�����Ⱦ��ϡ�͡���ɢ�����������Ӱ�죬�׸���ȺӦ�ʵ���������ʱ�䡣\",\"brf\":\"��\"},\"comf\":{\"txt\":\"����������ã������������������£���о�������ˬ�����ʣ����ƫ�ȡ�\",\"brf\":\"������\"},\"drsg\":{\"txt\":\"�����ȣ������Ŷ�ȹ���̿㡢�̱����ס�T�����ļ���װ��\",\"brf\":\"��\"},\"sport\":{\"txt\":\"�����Ϻã������˽��и����˶������������ȣ����ʵ������˶�ʱ�䣬�����˶�ǿ�ȡ�\",\"brf\":\"������\"},\"flu\":{\"txt\":\"���������������ˣ������Խ��¹��̣�������ð���ʽϵ͡�\",\"brf\":\"�ٷ�\"}},\"aqi\":{\"city\":{\"no2\":\"39\",\"pm25\":\"53\",\"o3\":\"66\",\"qlty\":\"��\",\"so2\":\"8\",\"aqi\":\"73\",\"pm10\":\"70\",\"co\":\"1.1\"}},\"basic\":{\"city\":\"�ɶ�\",\"update\":{\"loc\":\"2019-05-25 11:27\",\"utc\":\"2019-05-25 03:27\"},\"lon\":\"104.06573486\",\"id\":\"CN101270101\",\"cnty\":\"�й�\",\"lat\":\"30.65946198\"},\"daily_forecast\":[{\"date\":\"2019-05-25\",\"pop\":\"25\",\"hum\":\"63\",\"uv\":\"5\",\"vis\":\"19\",\"astro\":{\"ss\":\"19:58\",\"mr\":\"00:42\",\"ms\":\"11:28\",\"sr\":\"06:03\"},\"pres\":\"944\",\"pcpn\":\"0.0\",\"tmp\":{\"min\":\"19\",\"max\":\"29\"},\"cond\":{\"txt_n\":\"��\",\"code_n\":\"100\",\"code_d\":\"101\",\"txt_d\":\"����\"},\"wind\":{\"sc\":\"1-2\",\"spd\":\"4\",\"deg\":\"-1\",\"dir\":\"�޳�������\"}},{\"date\":\"2019-05-26\",\"pop\":\"4\",\"hum\":\"34\",\"uv\":\"12\",\"vis\":\"18\",\"astro\":{\"ss\":\"19:58\",\"mr\":\"01:20\",\"ms\":\"12:22\",\"sr\":\"06:03\"},\"pres\":\"950\",\"pcpn\":\"0.0\",\"tmp\":{\"min\":\"20\",\"max\":\"31\"},\"cond\":{\"txt_n\":\"��\",\"code_n\":\"100\",\"code_d\":\"100\",\"txt_d\":\"��\"},\"wind\":{\"sc\":\"1-2\",\"spd\":\"11\",\"deg\":\"-1\",\"dir\":\"�޳�������\"}},{\"date\":\"2019-05-27\",\"pop\":\"13\",\"hum\":\"56\",\"uv\":\"9\",\"vis\":\"20\",\"astro\":{\"ss\":\"19:59\",\"mr\":\"01:55\",\"ms\":\"13:16\",\"sr\":\"06:02\"},\"pres\":\"955\",\"pcpn\":\"0.0\",\"tmp\":{\"min\":\"16\",\"max\":\"28\"},\"cond\":{\"txt_n\":\"��\",\"code_n\":\"100\",\"code_d\":\"100\",\"txt_d\":\"��\"},\"wind\":{\"sc\":\"1-2\",\"spd\":\"10\",\"deg\":\"-1\",\"dir\":\"�޳�������\"}}],\"status\":\"ok\"}]}";
        //String result = json.formatJson(str);
        //System.out.println(result);
 
        //HTML��ʽ��
        //formatHtml("<!DOCTYPE html><html><head><meta charset=\"utf-8\"><title>����̳�(runoob.com)</title></head><body><h1>�ҵĵ�һ������</h1><p>�ҵĵ�һ�����䡣</p></body></html>");
    
    	//xml�ַ�����ʽ��
        //String str = "<?xml version=\"1.0\" encoding=\"GBK\"?><RequestData><HeadData><UserCode>sh1_admin</UserCode><UserName>sh1_admin</UserName><UserCompanyCode>3107</UserCompanyCode><UserCompanyName>�Ϻ��ֹ�˾һ��</UserCompanyName><RequestType>03</RequestType></HeadData><BodyData><ReportId>113100000033</ReportId><Insurant>a5rfg87</Insurant><NumberPlate>��E78612</NumberPlate><EngineModel></EngineModel><CarVin></CarVin><AccidentDate>2011-02-25 15:07:00</AccidentDate><ReportDate>2011-02-25 15:07:00</ReportDate><Province>310000</Province><City>310100</City><District></District><AccidentPlace>1</AccidentPlace><AccidentLongitude></AccidentLongitude><AccidentLatitude></AccidentLatitude><SurveyLongitude></SurveyLongitude><SurveyLatitude></SurveyLatitude><SceneReportFlag></SceneReportFlag><Reporter></Reporter><ReporterTel></ReporterTel><SurveyPlace></SurveyPlace><OperatorId>3525</OperatorId><OperatorName>sh_admin</OperatorName><ReportDealId>30000800</ReportDealId><ReportDealName>���շֹ�˾</ReportDealName><CompanyName></CompanyName><CustomerTypeCode></CustomerTypeCode><ForcePolicyId>a5rfg87a5rfg87a5rfg87</ForcePolicyId><BizPolicyId></BizPolicyId><Index>0</Index><FieldName>5</FieldName></BodyData></RequestData>";
        //formatXml(str);

    }

}
