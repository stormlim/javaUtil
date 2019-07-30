package util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * ��֤�������
 * @author limstorm
 * @create 2019-04-26 09:23:47
 */
public class Captcha {
	private static int width = 90;// ����ͼƬ��width
    private static int height = 20;// ����ͼƬ��height
    private static int codeCount = 4;// ����ͼƬ����ʾ��֤��ĸ���
    private static int xx = 15;
    private static int fontHeight = 18;
    private static  int codeY = 16;
    private static char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', '��', '��',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'W', 'X', 'Y', 'Z', 'P', 'Q', 'R', 'S', 'T', '��', 
            'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'L', 'M', 'N', 'O', 'X', '��', 'I', 'J', 'K', '��',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'Z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'T', 'U', 'V', '��',
            'V', 'W', 'X', 'Y', 'Z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', '��',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Y', 'Z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', '��',
            'N', 'O', 'P', 'Q', 'R','S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '��', 'һ', 'M', '��', 'L',  'U', 'R', '��', 'W', 'S','H', '��',
            '��', '��', '��', '��', '��', '��', '��', 'Ϊ', '��', '��', '��', '��', '��', 'Ҫ', '��', 'ʱ', '��', '��', '��', '��', '��', '��', '��', '��',
            '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', 'ͬ', '��', 'Ҳ', '��', '��', '��', '��', '˵', '��', '��', '��', '��', '��',
            '��', '��', '��', '��', 'ѧ', '��', '��', '��', '��', '��', 'ʮ', '��', '֮', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', 'ˮ',
            '��', '��', '��', '��', '��', '��', 'С', '��', '��', 'ʵ', '��', '��', '��', '��', '��', '��', '��', '��', 'ʹ', '��', '��', 'ҵ', '��', 'ȥ',
            '��', '��', '��', 'Ӧ', '��', '��', '��', '��', '��', '��', '��', 'Щ', 'Ȼ', 'ǰ', '��', '��', '��', '��', '��', '��', '��', '��', '��', 'ƽ',
            '��', '��', 'ȫ', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', 'ԭ', '��', 'ô', '��',
            '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', 'ֻ', 'û', '��', '��', '��', '��', '��', '��', '��', '��', 'ϵ', '��',
            '��', '��', '��', '��', '��', '��', '��', '��', 'ͨ', '��', '��', 'ֱ', '��', '��', '��', 'չ', '��', '��', '��', '��', 'Ա', '��', 'λ', '��',
            '��', '��', '��', '��', 'Ʒ', 'ʽ', '��', '��', '��', '��', '��', '��', '��', '��', '��', 'ͷ', '��', '��', '��', '��', '·', '��', '��', 'ͼ',
            'ɽ', 'ͳ', '��', '֪', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', 'ũ', 'ָ', '��', '��', '��', 'ǿ', '��',
            '��', '��', '��', '��', '��', '��', 'ս', '��', '��', '��', '��', 'ȡ', '��', '��', '��', '��', '��', 'ɫ', '��', '��', '��', '��', '��', '��',
            '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', 'ѹ', '־', '��', '��', '��', '��', '��', '��', '��', '˼', '��', '��', '��',
            '��', '��', 'ʲ', '��', '��', '��', 'Ȩ', '��', '֤', '��', '��', '��', '��', '��', '��', 'ת', '��', '��', '��', '��', '��', '��', '��', '��',
            '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', 'ÿ', 'Ŀ', '��', '��', '��', '��', 'ʾ', '��', '��', '��', '��', '��', '��',
            '��', '��', '��', '��', 'ȷ', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', 'Ԫ', '��', '��', '��', '��', '��', '��', '��', '��',
            'Ⱥ', '��', 'ʯ', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', 'Խ', '֯', 'װ', 'Ӱ', '��', '��', '��', '��', '��',
            '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', 'ǧ', '��', 'ί', '��', '��', '��', '��', '��',
            '��', 'ʡ', '��', 'ϰ', '��', 'Լ', '֧', '��', 'ʷ', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��',
            '̫', '׼', '��', 'ֵ', '��', '��', '��', 'ά', '��', 'ѡ', '��', 'д', '��', '��', 'ë', '��', '��', 'Ч', '˹', 'Ժ', '��', '��', '��', '��',
            '��', '��', '��', '��', '��', '��', '��', '��', 'Ƭ', 'ʼ', 'ȴ', 'ר', '״', '��', '��', '��', 'ʶ', '��', '��', 'Բ', '��', '��', 'ס', '��',
            '��', '��', '��', '��', '��', '��', 'ϸ',};
    
    /**
     * ����һ��map����
     * codeΪ���ɵ���֤��
     * codePicΪ���ɵ���֤��BufferedImage����
     * @return
     */
    public static Map<String,Object> generateCodeAndPic() {
        // ����ͼ��buffer
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D gd = buffImg.createGraphics();
        // Graphics2D gd = (Graphics2D) buffImg.getGraphics();
        //Graphics gd = buffImg.getGraphics();
        // ����һ���������������
        Random random = new Random();
        // ��ͼ�����Ϊ��ɫ
        gd.setColor(Color.WHITE);
        gd.fillRect(0, 0, width, height);

        // �������壬����Ĵ�СӦ�ø���ͼƬ�ĸ߶�������
        Font font = new Font("Fixedsys", Font.BOLD+Font.ITALIC, fontHeight);
        // �������塣
        gd.setFont(font);

        // ���߿�
        gd.setColor(Color.BLACK);
        gd.drawRect(0, 0, width - 1, height - 1);

        // �������40�������ߣ�ʹͼ���е���֤�벻�ױ���������̽�⵽��
        gd.setColor(Color.GRAY);
        for (int i = 0; i < 30; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            gd.drawLine(x, y, x + xl, y + yl);
        }

        // randomCode���ڱ��������������֤�룬�Ա��û���¼�������֤��
        StringBuffer randomCode = new StringBuffer();
        int red = 0, green = 0, blue = 0;

        // �������codeCount���ֵ���֤�롣
        for (int i = 0; i < codeCount; i++) {
            // �õ������������֤�����֡�
            String code = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
            // �����������ɫ������������ɫֵ�����������ÿλ���ֵ���ɫֵ������ͬ��
            red = random.nextInt(155);
            green = random.nextInt(155);
            blue = random.nextInt(155);
            // �������������ɫ����֤����Ƶ�ͼ���С�
            gd.setColor(new Color(red, green, blue));
            if(i==0)
            	gd.rotate(0.02);
            else if(i%2==1)
            	gd.rotate(-0.04);
            else
            	gd.rotate(0.04);
            gd.drawString(code, (i + 1) * xx, codeY);
            // ���������ĸ�����������һ��
            randomCode.append(code);
        }
        Map<String,Object> map  =new HashMap<String,Object>();
        //�����֤��
        map.put("code", randomCode);
        //������ɵ���֤��BufferedImage����
        map.put("codePic", buffImg);
        return map;
    }

    public static void main(String[] args) throws Exception {
    	OutputStream out = new FileOutputStream(System.getProperty("user.dir")+"/source/images/"+System.currentTimeMillis()+".jpg");
        Map<String,Object> map = Captcha.generateCodeAndPic();
        ImageIO.write((RenderedImage) map.get("codePic"), "jpeg", out);
        System.out.println("��֤���ֵΪ��"+map.get("code"));
    }
}
