package hello.servlet.basic.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * HTTP 요청의 Start Line 및 Header 정보를 조회하는 클래스
 */
@WebServlet(name = "requestHeaderServlet", urlPatterns = "/request-header")
public class RequestHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        printStartLine(request);
        printHeaders(request);
        printHeaderUtils(request);
        printEtc(request);
    }

    // Start line의 모든 정보 출력
    // HTTP method(GET, POST, PUT 등), Request target(URL), HTTP version 등이 있다.
    private void printStartLine(HttpServletRequest request) {
        System.out.println("--- REQUEST-LINE - start ---");

        System.out.println("request.getMethod() = " + request.getMethod()); // GET
        System.out.println("request.getProtocol() = " + request.getProtocol()); // HTTP/1.1
        System.out.println("request.getScheme() = " + request.getScheme()); // http
        System.out.println("request.getRequestURL() = " + request.getRequestURL()); // http://localhost:8080/request-header
        System.out.println("request.getRequestURI() = " + request.getRequestURI());// /request-header
        System.out.println("request.getQueryString() = " + request.getQueryString()); //username=hi
        System.out.println("request.isSecure() = " + request.isSecure()); //https 사용 유무

        System.out.println("--- REQUEST-LINE - end ---");
        System.out.println();
    }

    // Header의 모든 정보 표시
    private void printHeaders(HttpServletRequest request) {
        System.out.println("--- HEADERS - start ---");

        /*// 예전 방식
        Enumeration<String> headerNames = request.getHeaderNames();  // HTTP 요청 메시지(request)의 모든 header 정보를 가져옴
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            System.out.println(headerName + " = " + headerName);
        }*/

        // 최근 방식
        request.getHeaderNames().asIterator()
                .forEachRemaining(headerName -> System.out.println(headerName + " = " + headerName));

        System.out.println("--- HEADERS - end ---");
        System.out.println();

        // getHeaderNames() 대신 getHeader(name)을 통해 원하는 값만 조회할 수 있다.
        // request.getHeader("host");
    }

    // Header의 편리한 조회
    private void printHeaderUtils(HttpServletRequest request) {
        System.out.println("--- Header 편의 조회 start ---");

        System.out.println("[Host 편의 조회]");
        System.out.println("request.getServerName() = " + request.getServerName()); //Host 헤더
        System.out.println("request.getServerPort() = " + request.getServerPort()); //Host 헤더
        System.out.println();

        System.out.println("[Accept-Language 편의 조회]");
        request.getLocales().asIterator()
                .forEachRemaining(locale -> System.out.println("locale = " + locale));
        System.out.println("request.getLocale() = " + request.getLocale());  // 가장 높은 우선순위를 가진 Locale
        System.out.println();

        System.out.println("[cookie 편의 조회]");
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                System.out.println(cookie.getName() + ": " + cookie.getValue());
            }
        }
        System.out.println();

        System.out.println("[Content 편의 조회]");
        System.out.println("request.getContentType() = " + request.getContentType());
        System.out.println("request.getContentLength() = " + request.getContentLength());
        System.out.println("request.getCharacterEncoding() = " + request.getCharacterEncoding());

        System.out.println("--- Header 편의 조회 end ---");
        System.out.println();
    }

    //기타 정보: HTTP 메시지의 정보는 아니며, Cilent와 Server 간 Connection에 대한 정보이다.
    private void printEtc(HttpServletRequest request) {
        System.out.println("--- 기타 조회 start ---");

        System.out.println("[Remote 정보]");  // 요청한 클라이언트에 대한 정보
        System.out.println("request.getRemoteHost() = " + request.getRemoteHost()); //
        System.out.println("request.getRemoteAddr() = " + request.getRemoteAddr()); //
        System.out.println("request.getRemotePort() = " + request.getRemotePort()); //
        System.out.println();

        System.out.println("[Local 정보]");  // 현재 서버에 대한 정보
        System.out.println("request.getLocalName() = " + request.getLocalName()); //
        System.out.println("request.getLocalAddr() = " + request.getLocalAddr()); //
        System.out.println("request.getLocalPort() = " + request.getLocalPort()); //

        System.out.println("--- 기타 조회 end ---");
        System.out.println();
    }
}
