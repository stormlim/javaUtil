package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackInputStream;
import java.io.Reader;
import java.util.Date;
import java.util.HashMap;

import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.baidu.aip.util.Util;

/**
 * jar包：java-sdk-4.11.0(百度SDK),slf4j-api,json-20180130,
 * 百度语音处理,开发文档：https://ai.baidu.com/docs#/TTS-Online-Java-SDK/02005b58
 * @author limstorm
 * @create 2019-05-24 12:22:16
 */
public class Voice {

	//设置APPID/AK/SK
    public static final String APP_ID = "16329221";
    public static final String API_KEY = "mdboieNHDEn6Fsmn4VGl2Xgg";
    public static final String SECRET_KEY = "asoq44n0HUhYTSkkEWdh9Tp6fnNVsYYw";

    /**
     * 文本语音合成
     * @param text 需要合成文本信息
     */
    public static void SpeechSynthesis(String text) {
        // 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

        // 设置可选参数
    	HashMap<String, Object> options = new HashMap<String, Object>();
        options.put("spd", "6");
        options.put("pit", "5");
        options.put("per", "3");
        // 调用接口
        TtsResponse res = client.synthesis(text, "zh", 1, options);
        byte[] data = res.getData();
        if (data != null) {
            try {
                Util.writeBytesToFileSystem(data, System.getProperty("user.dir")+"/source/mp3/"+new Date().getTime()+".mp3");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 文件语音合成
     * @param fileName
     */
    public static void FileSpeechSynthesis(String filePath) {
    	File file = new File(filePath);
    	BufferedReader bf;
    	String line = null;
		try {
			bf = new BufferedReader(new UnicodeReader(new FileInputStream(file),"UTF-8"));
			while((line = bf.readLine())!=null) {
				SpeechSynthesis(line);
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void main(String[] args) {
    	FileSpeechSynthesis(System.getProperty("user.dir")+"/source/txt/九星霸体诀.txt");
    }
}


/**
version: 1.1 / 2007-01-25
- changed BOM recognition ordering (longer boms first)

Original pseudocode   : Thomas Weidenfeller
Implementation tweaked: Aki Nieminen

http://www.unicode.org/unicode/faq/utf_bom.html
BOMs:
  00 00 FE FF    = UTF-32, big-endian
  FF FE 00 00    = UTF-32, little-endian
  EF BB BF       = UTF-8,
  FE FF          = UTF-16, big-endian
  FF FE          = UTF-16, little-endian

Win2k Notepad:
  Unicode format = UTF-16LE
***/

/**
* Generic unicode textreader, which will use BOM mark
* to identify the encoding to be used. If BOM is not found
* then use a given default or system encoding.
*/
class UnicodeReader extends Reader {
  PushbackInputStream internalIn;
  InputStreamReader   internalIn2 = null;
  String              defaultEnc;

  private static final int BOM_SIZE = 4;

  /**
   *
   * @param in  inputstream to be read
   * @param defaultEnc default encoding if stream does not have 
   *                   BOM marker. Give NULL to use system-level default.
   */
  UnicodeReader(InputStream in, String defaultEnc) {
     internalIn = new PushbackInputStream(in, BOM_SIZE);
     this.defaultEnc = defaultEnc;
  }

  public String getDefaultEncoding() {
     return defaultEnc;
  }

  /**
   * Get stream encoding or NULL if stream is uninitialized.
   * Call init() or read() method to initialize it.
   */
  public String getEncoding() {
     if (internalIn2 == null) return null;
     return internalIn2.getEncoding();
  }

  /**
   * Read-ahead four bytes and check for BOM marks. Extra bytes are
   * unread back to the stream, only BOM bytes are skipped.
   */
  protected void init() throws IOException {
     if (internalIn2 != null) return;

     String encoding;
     byte bom[] = new byte[BOM_SIZE];
     int n, unread;
     n = internalIn.read(bom, 0, bom.length);

     if ( (bom[0] == (byte)0x00) && (bom[1] == (byte)0x00) &&
                 (bom[2] == (byte)0xFE) && (bom[3] == (byte)0xFF) ) {
        encoding = "UTF-32BE";
        unread = n - 4;
     } else if ( (bom[0] == (byte)0xFF) && (bom[1] == (byte)0xFE) &&
                 (bom[2] == (byte)0x00) && (bom[3] == (byte)0x00) ) {
        encoding = "UTF-32LE";
        unread = n - 4;
     } else if (  (bom[0] == (byte)0xEF) && (bom[1] == (byte)0xBB) &&
           (bom[2] == (byte)0xBF) ) {
        encoding = "UTF-8";
        unread = n - 3;
     } else if ( (bom[0] == (byte)0xFE) && (bom[1] == (byte)0xFF) ) {
        encoding = "UTF-16BE";
        unread = n - 2;
     } else if ( (bom[0] == (byte)0xFF) && (bom[1] == (byte)0xFE) ) {
        encoding = "UTF-16LE";
        unread = n - 2;
     } else {
        // Unicode BOM mark not found, unread all bytes
        encoding = defaultEnc;
        unread = n;
     }    
     //System.out.println("read=" + n + ", unread=" + unread);

     if (unread > 0) internalIn.unread(bom, (n - unread), unread);

     // Use given encoding
     if (encoding == null) {
        internalIn2 = new InputStreamReader(internalIn);
     } else {
        internalIn2 = new InputStreamReader(internalIn, encoding);
     }
  }

  public void close() throws IOException {
     init();
     internalIn2.close();
  }

  public int read(char[] cbuf, int off, int len) throws IOException {
     init();
     return internalIn2.read(cbuf, off, len);
  }

}