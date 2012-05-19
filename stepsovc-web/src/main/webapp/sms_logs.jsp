<html>
<head>
    <title>STEPs OVC</title>
    <script type="text/javascript" src="js/lib/jquery-1.6.2.min.js"></script>
    <script type="text/javascript" src="js/lib/jquery-ui-1.8.16.custom.min.js"></script>
    <script type="text/javascript" src="js/audit.js"></script>
    <link rel="stylesheet" type="text/css" href="css/lib/jquery-ui-1.8.16.custom.css"
          media="screen, projection, print"/>
</head>
<body>
<div class="content">

    <div class="logo_center">
    </div>
    <div>
        <span align="left" class="title">STEPs OVC</span>
    </div>
    </br>
    <div id="tabs">
        <ul>
            <li><a id="tab1" href="#tabs-1">Logs</a></li>
        </ul>
        <div id="tabs-1">
            <div class="audit_box">
                <form id="audit-form">
                    Audits :
                    <select id="audit_options">
                        <option value="sms/outbound">Outbound SMS</option>
                        <option value="sms/inbound">Inbound SMS</option>
                    </select>
                    <input id="refresh_audit" type="button" value="Refresh"/>
                </form>
                <div id="audit_table"></div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
