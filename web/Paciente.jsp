<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import = "modelo.Profesional"%>
<%
    HttpSession sesion = request.getSession();
    Profesional emp = (Profesional) sesion.getAttribute("user");
    
    if(emp != null) {
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
        <title>PRODUCTOS</title>
    </head>
    <body>
        <div class="d-flex">
            <div class="card col-sm-4">
                <div class="card_body">
                    <form action="Controlador?menu=Producto" method="POST">
                        <div class="form-group">
                            <label>Descripcion</label>
                            <input type="text" value="${producto.getDescripcion()}" name="txtDes" class="form-control">
                        </div>

                        <div class="form-group">
                            <label>Precio</label>
                            <input type="text" value="${producto.getPrecio()}" name="txtPrecio" class="form-control">
                        </div>

                        <div class="form-group">
                            <label>Stock</label>
                            <input type="text" value="${producto.getStock()}" name="txtSto" class="form-control">
                        </div>

                        <div class="form-group">
                            <label>Estado</label>
                            
                            <select name="txtStt" class="form-control">
                                <option></option>
                                <option>Disponible</option>
                                <option>Agotado</option>
                            </select>
                        </div>
                        
                        <input type="submit" name="accion" value="Agregar" class="btn btn-info">
                        <input type="submit" name="accion" value="Actualizar" class="btn btn-success">
                    </form>
                </div>
            </div>

            <div class="col-sm-8">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>DESCRIPCION</th>
                            <th>PRECIO</th>
                            <th>STOCK</th>
                            <th>ESTADO</th>
                            <th>ACCIONES</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="pr" items="${productos}">
                            <tr>
                                <td>${pr.getId()}</td>
                                <td>${pr.getDescripcion()}</td>
                                <td>${pr.getPrecio()}</td>
                                <td>${pr.getStock()}</td>
                                <td>${pr.getEstado()}</td>
                                <td>
                                    <a class="btn btn-warning" href="Controlador?menu=Producto&accion=Editar&id=${pr.getId()}">Editar</a>
                                    <a class="btn btn-danger" href="Controlador?menu=Producto&accion=Borrar&id=${pr.getId()}">Borrar</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>   
    </body>
    
    <%
        } else {
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    %>
</html>
