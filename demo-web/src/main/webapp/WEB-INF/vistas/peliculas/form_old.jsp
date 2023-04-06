<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ include file="../parts/header.jsp" %>
<sf:form modelAttribute="elemento" action="${pageContext.request.contextPath}/${action}">
	<div class="form-group">
		<sf:label path="filmId">Código:</sf:label>
		<c:if test = '${modo == "add"}'>
			<sf:input path="filmId" cssClass="form-control" cssErrorClass="is-invalid form-control"/>
			<sf:errors path="filmId" cssClass="invalid-feedback" />
		</c:if>
		<c:if test = '${modo != "add"}'>
			${elemento.filmId}
		</c:if>		
	</div>
	<div class="form-group">
		<sf:label path="title">Nombre:</sf:label>
		<sf:input required="required" path="title" cssClass="form-control" cssErrorClass="is-invalid form-control"/>
		<sf:errors path="title" cssClass="invalid-feedback" />
	</div>
	<div class="form-group">
		<sf:label path="description">Apellidos:</sf:label>
		<sf:input path="description" cssClass="form-control" cssErrorClass="is-invalid form-control"/>
		<sf:errors path="description" cssClass="invalid-feedback" />
	</div>
	<div class="form-group">
		<sf:label path="lastUpdate">Fecha:</sf:label>
		<sf:input type="date" path="lastUpdate" cssClass="form-control" cssErrorClass="is-invalid form-control"/>
		<sf:errors path="lastUpdate" cssClass="invalid-feedback" />
	</div>
	<div class="form-group">
		<input type="submit" value="Enviar" class="btn btn-primary">
		<a href="/actores" class="btn btn-primary" >Volver</a>
	</div>
</sf:form>
<%@ include file="../parts/footer.jsp" %>
