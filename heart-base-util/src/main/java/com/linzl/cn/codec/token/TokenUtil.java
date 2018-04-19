package com.linzl.cn.codec.token;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

/**
 * 支持跨域访问: Cookie是不允许垮域访问的，这一点对Token机制是不存在的，前提是传输的用户认证信息通过HTTP头传输.
 * <p>
 * 无状态(也称：服务端可扩展行):Token机制在服务端不需要存储session信息，因为Token
 * 自身包含了所有登录用户的信息，只需要在客户端的cookie或本地介质存储状态信息.
 * <p>
 * 更适用CDN: 可以通过内容分发网络请求你服务端的所有资料（如：javascript，HTML,图片等），而你的服务端只要提供API即可.
 * <p>
 * 去耦: 不需要绑定到一个特定的身份验证方案。Token可以在任何地方生成，只要在你的API被调用的时候，你可以进行Token生成调用即可.
 * <p>
 * 更适用于移动应用: 当你的客户端是一个原生平台（iOS, Android，Windows
 * 8等）时，Cookie是不被支持的（你需要通过Cookie容器进行处理），这时采用Token认证机制就会简单得多。
 * <p>
 * CSRF:因为不再依赖于Cookie，所以你就不需要考虑对CSRF（跨站请求伪造）的防范。
 * <p>
 * 性能: 一次网络往返时间（通过数据库查询session信息）总比做一次HMACSHA256计算 的Token验证和解析要费时得多.
 * <p>
 * 不需要为登录页面做特殊处理: 如果你使用Protractor 做功能测试的时候，不再需要为登录页面做特殊处理.
 * <p>
 * 基于标准化:你的API可以采用标准化的 JSON Web Token (JWT). 这个标准已经存在多个后端库（.NET, Ruby,
 * Java,Python, PHP）和多家公司的支持（如：Firebase,Google, Microsoft）.
 * 
 * @author linzl
 * 
 *         JWT的组成:一个JWT实际上就是一个字符串，它由三部分组成，头部、载荷与签名。
 *
 *         2017年4月28日
 */
public class TokenUtil {
	private static Key key = MacProvider.generateKey();

	/**
	 * 生成Token
	 * 
	 * @return
	 */
	public static String createToken() {
		JwtBuilder builder = Jwts.builder()
				// 该JWT的发行机构
				.setIssuer("http://trustyapp.com/")
				// 发行时间
				.setIssuedAt(new Date())
				// 该JWT所面向的用户
				.setSubject("users/1300819380")
				// 失效时间 6分钟*60*1000
				.setExpiration(new Date(System.currentTimeMillis() + 360000))
				// 接收该JWT的一方
				.setAudience("hello")
				// 如果当前时间在nbf里的时间之前，则Token不被接受；一般都会留一些余地，比如几分钟；，是否使用是可选的
				.setNotBefore(new Date())
				// 定义额外的属性
				.claim("user_id", "用户ID")

				.compressWith(CompressionCodecs.DEFLATE)
				// 签名算法，及key
				.signWith(SignatureAlgorithm.HS512, key);

		return builder.compact();
	}

	/**
	 * 解析Token
	 * 
	 * @param compactJws
	 * @return
	 */
	public static String parseToken(String compactJws) {
		try {
			// require表示必须包含该属性
			Jws<Claims> claims = Jwts.parser().requireSubject("users/1300819380").require("user_id", "11-用户ID")
					.setSigningKey(key).parseClaimsJws(compactJws);

			String head = claims.getHeader().toString();
			System.out.println("head==>" + head);

			// 用户添加的内容，这些需要拿来解析
			String body = claims.getBody().toString();
			System.out.println("body==>" + body);

			String sign = claims.getSignature().toString();
			System.out.println("sign==>" + sign);
		} catch (MissingClaimException e) {
			// we get here if the required claim is not present
			e.printStackTrace();
		} catch (IncorrectClaimException e) {
			// we get here if the required claim has the wrong value
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		String token = TokenUtil.createToken();
		TokenUtil.parseToken(token);
	}
}
