<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ include file="../parts/header.jsp" %>
<h1>Listado de Peliculas</h1>

<table class="table table-hover table-striped table-bordered">
	<tr>
		<th>Peliculas</th>
		<td><a class="btn btn-primary" href="/peliculas/add"><i class="fas fa-plus"></i></a></td>
	</tr>
	<c:forEach var="elemento" items="${listado.getContent()}">
		<tr>
			<td><a href="/peliculas/${elemento.filmId}/${elemento.title}">${elemento.title}</a></td>
			<td>
				<a href="/peliculas/${elemento.filmId}/edit" class="btn btn-success"><i class="fas fa-pen"></i></a>
				<a href="/peliculas/${elemento.filmId}/delete"class="btn btn-danger"><i class="fas fa-trash"></i></a>
			</td>
		</tr>
	</c:forEach>
</table>
<ul class="pagination">
  	<c:if test = "${listado.hasPrevious()}">
	    <li class="page-item">
	    	<a class="page-link" href="${pageContext.request.contextPath}/peliculas?page=${listado.getNumber()-1}">
	    		<span aria-hidden="true">&laquo;</span>
	    	</a>
	    </li>
	</c:if>
	<c:forEach var = "i" begin = "0" end = "${listado.getTotalPages() -1}">
    	<li class="page-item<c:if test = "${i==listado.getNumber()}"> active</c:if>">
    		<a class="page-link" href="${pageContext.request.contextPath}/peliculas?page=${i}">${i + 1}</a>
    	</li>
	</c:forEach>
  	<c:if test = "${listado.hasNext()}">
	    <li class="page-item">
	    	<a class="page-link" href="${pageContext.request.contextPath}/peliculas?page=${listado.getNumber()+1}">
	    		<span aria-hidden="true">&raquo;</span>
	    	</a>
	    </li>
	</c:if>
</ul>

<%@ include file="../parts/footer.jsp" %>
