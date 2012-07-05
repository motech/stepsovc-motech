<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Date" %>
<%
    String os = (String) System.getenv().get("OS");
    if (os == null || os.indexOf("indows") < 0) {
        try {
            String fakeDate = request.getParameter("fakeDate");
            try {
                System.deregisterFakeCurrentTimeMillis();
            } catch (java.lang.Exception e) {
                out.println("Error: " + e.getMessage());
                return;
            }
            out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            return;
        } catch (Exception ignore) {
        }
    }
%>