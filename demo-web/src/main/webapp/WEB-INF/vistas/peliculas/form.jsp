<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ include file="../parts/header.jsp" %>
<sf:form modelAttribute="elemento" action="${pageContext.request.contextPath}/${action}">
	<sf:errors cssClass="d-block p-2 bg-danger text-white"/>
	<div class="form-group">
		<sf:label path="filmId"><s:message code="peliculas.form.id" /></sf:label>
		<c:if test = '${modo == "add"}'>
			<sf:input path="filmId" cssClass="form-control" cssErrorClass="is-invalid form-control"/>
			<sf:errors path="filmId" cssClass="invalid-feedback" />
		</c:if>
		<c:if test = '${modo != "add"}'>
			${elemento.actorId}
			<sf:hidden path="filmId"/>
			<sf:errors path="filmId" cssClass="error" />
		</c:if>		
	</div>
	<div class="form-group">
		<sf:label path="title"><s:message code="peliculas.form.title" /></sf:label>
		<sf:input required="required" path="title" cssClass="form-control" cssErrorClass="is-invalid form-control"/>
		<sf:errors path="title" cssClass="invalid-feedback" />
	</div>
	<div class="form-group">
		<sf:label path="description"><s:message code="peliculas.form.description" /></sf:label>
		<sf:input path="description" cssClass="form-control" cssErrorClass="is-invalid form-control"/>
		<sf:errors path="description" cssClass="invalid-feedback" />
	</div>
	<div class="form-group">
		<input type="submit" value="<s:message code="form.enviar" />" class="btn btn-primary">
		<a href="/peliculas" class="btn btn-primary" ><s:message code="form.volver" /></a>
	</div>
</sf:form>
<%@ include file="../parts/footer.jsp" %>
