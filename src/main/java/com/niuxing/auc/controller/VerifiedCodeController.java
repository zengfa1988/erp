package com.niuxing.auc.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author ds
 *
 */
@Controller
public class VerifiedCodeController {


	/**
	 * 简单密码 
	 */
	private final static String[] simplepwd = new String[] { "123456", "234567", "345678", "456789", "987654", "876543", "765432", "654321" };

	private int width = 90;// 定义图片的width
	private int height = 20;// 定义图片的height
	private int codeCount = 4;// 定义图片上显示验证码的个数
	private int xx = 15;
	private int fontHeight = 18;
	private int codeY = 16;
	char[] codeSequence = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	public static final String SESSION_RANDOMCODE = "randomCode"; // 验证码
	/**
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping("/code.do")
	public void getCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		// 定义图像buffer
		BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		Graphics gd = buffImg.getGraphics();
		// 创建一个随机数生成器类
		Random random = new Random();
		// 将图像填充为白色
		gd.setColor(Color.WHITE);
		gd.fillRect(0, 0, width, height);

		// 创建字体，字体的大小应该根据图片的高度来定。
		Font font = new Font("Fixedsys", Font.BOLD, fontHeight);
		// 设置字体。
		gd.setFont(font);

		// 画边框。
		gd.setColor(Color.BLACK);
		gd.drawRect(0, 0, width - 1, height - 1);

		// 随机产生40条干扰线，使图象中的认证码不易被其它程序探测到。
		gd.setColor(Color.BLACK);
		for (int i = 0; i < 15; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			int xl = random.nextInt(14);
			int yl = random.nextInt(14);
			gd.drawLine(x, y, x + xl, y + yl);
		}

		// randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
		StringBuilder randomCode = new StringBuilder();
		int red ;
		int green ;
		int blue ;
		// 随机产生codeCount数字的验证码。
		for (int i = 0; i < codeCount; i++) {
			// 得到随机产生的验证码数字。
			String code = String.valueOf(codeSequence[random.nextInt(10)]);
			// 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
			red = random.nextInt(120);
			green = random.nextInt(120);
			blue = random.nextInt(120);

			// 用随机产生的颜色将验证码绘制到图像中。
			gd.setColor(new Color(red, green, blue));
			gd.drawString(code, (i + 1) * xx, codeY);

			// 将产生的四个随机数组合在一起。
			randomCode.append(code);
		}
		
		String loginName = req.getParameter("loginName");
//        RedisSlave.getInstance().setObject( UserConstants.AUC_IMAGE_NR_CODE + loginName,
//                SerializeUtil.serialize( randomCode.toString().toLowerCase() ), UserConstants.PHONE_EXPIRE_TIME );
		// 禁止图像缓存。
		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);

		resp.setContentType("image/jpeg");

		// 将图像输出到Servlet输出流中。
		ServletOutputStream sos = resp.getOutputStream();
		ImageIO.write(buffImg, "jpeg", sos);
		sos.close();
	}

	/**
	 * 
	 * @param password
	 * @return
	 */
	public static boolean checkPwd(String password) {
		boolean bl = true;

		if (Pattern.matches("^([0-9a-zA-Z])\1{5}$", password) && Pattern.matches("^[a-zA-Z0-9]{6,16}$", password)) {
			return false;
		}
		for (String s : simplepwd) {
			if (s.equals(password)) {
				bl = false;
				break;
			}
		}
		return bl;
	}

}
