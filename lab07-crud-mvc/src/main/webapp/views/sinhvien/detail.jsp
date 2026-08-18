<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h2>Chi tiết sinh viên</h2>
<table border="1" cellpadding="6">
    <tr><th>ID</th><td>${sv.id}</td></tr>
    <tr><th>Mã sinh viên</th><td>${sv.maSinhVien}</td></tr>
    <tr><th>Họ tên</th><td>${sv.hoTen}</td></tr>
    <tr><th>Email</th><td>${sv.email}</td></tr>
    <tr><th>Lớp</th><td>${sv.lop}</td></tr>
</table>
<p>
    <a href="${pageContext.request.contextPath}/sinh-vien?action=edit&id=${sv.id}">Sửa</a> |
    <a href="${pageContext.request.contextPath}/sinh-vien">Quay lại danh sách</a>
</p>