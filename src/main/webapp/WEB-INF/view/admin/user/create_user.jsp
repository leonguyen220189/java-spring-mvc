<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Dashboard - Table User</title>
                <link href="/css/styles.css" rel="stylesheet" />
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
            </head>

            <body class="sb-nav-fixed">
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav">
                    <jsp:include page="../layout/sidebar.jsp" />
                    <div id="layoutSidenav_content">
                        <main>
                            <div class="container-fluid px-4">
                                <h1 class="mt-4">Manage Users</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                                    <li class="breadcrumb-item active">Users</li>
                                </ol>
                                <div>
                                    <div class="row">
                                        <div class="col-md-10 col-12 mx-auto">
                                            <h3>Create A User</h3>
                                            <hr>
                                            <form:form action="/admin/user/create_user" method="post"
                                                modelAttribute="newUser">
                                                <div class="d-flex justify-content-between">
                                                    <div class="mb-3 col-md-5 col-12">
                                                        <label class="form-label">Email address</label>
                                                        <form:input type="email" class="form-control" path="email" />
                                                    </div>
                                                    <div class="mb-3 col-md-5 col-12">
                                                        <label class="form-label">Password</label>
                                                        <form:input type="password" class="form-control"
                                                            path="password" />
                                                    </div>
                                                </div>
                                                <div class="d-flex justify-content-between">
                                                    <div class="mb-3 col-md-5 col-12">
                                                        <label class="form-label">Phone Number</label>
                                                        <form:input type="text" class="form-control"
                                                            path="phoneNumber" />
                                                    </div>
                                                    <div class="mb-3 col-md-5 col-12">
                                                        <label class="form-label">Full Name</label>
                                                        <form:input type="text" class="form-control" path="fullName" />
                                                    </div>
                                                </div>
                                                <div class="mb-3 col-md-12">
                                                    <label class="form-label">Address</label>
                                                    <form:input type="text" class="form-control" path="address" />
                                                </div>
                                                <div class="d-flex justify-content-between">
                                                    <div class="mb-3 col-md-5 col-12">
                                                        <label class="form-label">Role</label>
                                                        <select class="form-select">
                                                            <option value="ADMIN" selected>ADMIN</option>
                                                            <option value="USER">USER</option>
                                                        </select>
                                                    </div>
                                                    <div class="mb-3 col-md-5 col-12">
                                                        <label for="avatarFile" class="form-label">Avatar</label>
                                                        <div class="input-group mb-3">
                                                            <input type="file" class="form-control" id="avatarFile"
                                                                accept=".png, .jpg, .jpeg">
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-12 mb3">
                                                    <img style="max-height: 250px; display: none;" alt="avatar preview"
                                                        id="avatarPreview">
                                                </div>
                                                <div class="col-12 mb3">
                                                    <button type="submit" class="btn btn-primary">Submit</button>
                                                </div>
                                            </form:form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </main>
                        <jsp:include page="../layout/footer.jsp" />
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="js/scripts.js"></script>
            </body>

            </html>