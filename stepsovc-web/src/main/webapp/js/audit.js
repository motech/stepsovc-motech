$.SmsAudit = function () {
    var hitAudit = function () {
        var url = 'audits/' + $('#audit_options').val();
        $.ajax({
            url:url,
            dataType:'html',
            success:updateAuditsTable
        });
        return false;
    };
    var updateAuditsTable = function (response) {
        $('#audit_table').html(response);
    };

    var bootstrap = function () {
        hitAudit();
        $('#audit_options').change(hitAudit);
        $('#refresh_audit').click(hitAudit);
    };
    $(bootstrap);
};

$(document).ready(function () {
    $("#tabs").tabs();
    new $.SmsAudit();
});
