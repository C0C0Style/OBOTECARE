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
        <title>Sales</title>
        <style>
            @media print{
                .parte1, .btn,.accion{
                    display: none;
                }
               
            }
        </style>
    </head>

    <body>
        <div class="d-flex">
            <div class="col-LG-5 parte01">
                <div class="card">
                    <form action="Controlador?menu=NuevaVenta" method="POST">
                        <div class="card-body">
                            <div class="form-group">
                                <label>Informacion Cliente</label>
                            </div>

                            <div class="form-group d-flex">
                                <div class="col-sm-6 d-flex">
                                    <input type="text" name="codigocliente" value="${c.getDni()}" class="form-control" placeholder="Code">
                                    <input type="submit" name="accion" value="Search" class="btn btn-outline-dark">
                                </div>

                                <div class="col-sm-6">
                                    <input type="text" name="nombrescliente" value="${c.getNombres()}" class="form-control" placeholder="Client Data">
                                </div>
                            </div>

                            <!-- DATOS DE LOS PRODUCTOS -->
                            <div class="form-group">
                                <label>Informacion Producto</label>
                            </div>

                            <div class="form-group d-flex">
                                <div class="col-sm-6 d-flex">
                                    <input type="text" name="codigoproducto" class="form-control" placeholder="Code">
                                    <button type="submit" name="accion" value="ProductSearch" class="btn btn-outline-dark">Buscar</button>
                                </div>

                                <div class="col-sm-6">
                                    <input type="text" name="nomproducto" value="${producto.getDescripcion()}" class="form-control" placeholder="Product Data">
                                </div>
                            </div>

                            <div class="form-group d-flex">
                                <div class="col-sm-6 d-flex">
                                    <input type="text" name="precio" value="${producto.getPrecio()}" class="form-control" placeholder="$0.00">
                                </div>

                                <div class="col-sm-3">
                                    <input type="number" name="cant" value="1" class="form-control" placeholder="Qty">
                                </div>

                                <div class="col-sm-3">
                                    <input type="text" name="stock" value="${producto.getStock()}" class="form-control" placeholder="Stock">
                                </div>
                            </div>

                            <!-- BOTON AGREGAR PRODUCTO AL REGISTRO -->
                            <div class="form-group">
                                <button type="submit" name="accion" value="Agregar" class="btn btn-outline-dark">Añadir Producto</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>

            <div class="col-sm-7">
                <div class="card">
                    <div class="card-body">

                        <div class="d-flex col-sm-6 ml-auto">
                            <label>Numero Serial</label>
                            <input type="text" name="nroserie" value="${nserie}" class="form-control">
                        </div>

                        <br>

                        <table class="table table-hover">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Codigo</th>
                                    <th>Descripcion</th>
                                    <th>Precio</th>
                                    <th>Cantidad</th>
                                    <th>Subtotal</th>
                                    <th class="accion">Acciones</th>
                                </tr>
                            </thead>

                            <tbody>
                                <c:forEach var="list" items="${lista}">
                                    <tr>
                                        <td>${list.getItem()}</td>
                                        <td>${list.getIdproducto()}</td>
                                        <td>${list.getDescripcionP()}</td>
                                        <td>${list.getPrecio()}</td>
                                        <td>${list.getCantidad()}</td>
                                        <td>${list.getSubtotal()}</td>
                                        <td class="d-flex">
                                            <a href="#" class="btn btn-warning">Editar</a>
                                            <a href="Controlador?menu=Venta&accion=Borrar&id" class="btn btn-danger"style="margin-left: 10px">Borrar</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <div class="card-footer d-flex">
                        <div class="col-sm-6">
                            <a href="Controlador?menu=NuevaVenta&accion=GenerarVenta" onclick="print()" class="btn btn-success">Venta</a>
                            <input type="submit" name="accion" value="Cancel" class="btn btn-danger">
                        </div>

                        <div class="col-sm-4 ml-auto d-flex">
                            <label style="margin-right: 10px">Total</label>
                            <input type="text" value="S/. ${total}" name="txtTotal" class="form-control">
                        </div>
                    </div>
                </div>
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

