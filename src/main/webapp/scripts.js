$(document).ready(function() {
    $(".delete-link").click(function(e) {
        e.preventDefault();
        var row = $(this).closest("tr");
        var name = row.find("td:eq(0)").text(); // Assuming name is in the first column

        $.ajax({
            type: "POST",
            url: "DeleteServlet",
            data: { name: name },
            success: function(response) {
                if (response === "success") {
                    row.remove();
                }
            }
        });
    });
});
