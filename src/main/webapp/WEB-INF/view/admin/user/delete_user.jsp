<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Create User</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
            </head>

            <body>
                <div class="container mt-5">
                    <div class="row">
                        <div class="col-md-6 col-12 mx-auto">
                            <h3>Delete A User (id = ${deletedUser.id})</h3>
                            <hr>
                            <form:form action="/admin/user/delete_user" method="post" modelAttribute="deletedUser">
                                <div class="alert alert-danger" role="alert">
                                    A simple danger alert—check it out!
                                </div>
                                <div class="d-flex justify-content-between">
                                    <a href="/admin/user" class="btn btn-success mt-2">Back</a>
                                    <button type="submit" class="btn btn-primary">Comfirm</button>
                                </div>
                            </form:form>
                        </div>
                    </div>
                </div>
            </body>

            </html>