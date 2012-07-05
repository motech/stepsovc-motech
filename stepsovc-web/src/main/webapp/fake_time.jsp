<%@page import="java.text.SimpleDateFormat" %>
<%@page import="java.util.Date" %>
<%
    boolean fakeTimeAvailable = false;
    String os = (String) System.getenv().get("OS");
    if (os == null || os.indexOf("indows") < 0) {
        try {
            java.lang.reflect.Method m = java.lang.ClassLoader.class.getDeclaredMethod("loadLibrary", Class.class, String.class, Boolean.TYPE);
            m.setAccessible(true);
            m.invoke(null, java.lang.System.class, "jvmfaketime", false);
            System.registerFakeCurrentTimeMillis();

            String fakeDate = request.getParameter("fakeDate");
            try {
                Date dateTimeValue = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(fakeDate);
                System.out.println("Posted date " + fakeDate);

                System.deregisterFakeCurrentTimeMillis();

                long diffValue = (dateTimeValue.getTime() - System.currentTimeMillis());

                System.registerFakeCurrentTimeMillis();
                System.out.println("offset calculated " + diffValue);
                System.setTimeOffset(diffValue);
                System.out.println("Date :" + new Date());
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