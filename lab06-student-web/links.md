<!-- Hướng Chạy các trang -->
# Link nhanh - Lab 6 MiniStudent Web

> Cách dùng: giữ Ctrl rồi bấm chuột trái vào link để mở thẳng ra trình duyệt mặc định.
> Nhớ đảm bảo Tomcat đang chạy trước khi bấm link( 2 cách chạy deploy.bat ):
>Cách 1: mở thư mục và click vào file deploy.bat / Cách 2: Mở terminal và nhập lệnh trong ngoặc (.\deploy.bat)

## Trang chính

- [Trang đăng nhập (login.jsp)](http://localhost:8080/lab06-student-web/login.jsp)
- [Trang chào (welcome.jsp) - cần đăng nhập trước](http://localhost:8080/lab06-student-web/welcome.jsp)
- [Đăng xuất](http://localhost:8080/lab06-student-web/logout)

## Sinh viên

- [Danh sách sinh viên (/students) - cần đăng nhập trước](http://localhost:8080/lab06-student-web/students)
- [Form thêm sinh viên (student-form.jsp) - cần đăng nhập trước](http://localhost:8080/lab06-student-web/student-form.jsp)

## Bài 1

- [Hello Servlet (/hello)](http://localhost:8080/lab06-student-web/hello)

## Kiểm tra server
Trang chủ Tomcat (kiểm tra Tomcat có chạy không) http://localhost:8080/


## Tài khoản test

- Username: `admin`
- Password: `123456`

## Ghi chú

- Mỗi lần sửa code xong, nhớ chạy `deploy.bat` trước khi bấm lại các link ở trên,
  nếu không sẽ thấy code cũ hoặc lỗi kết nối.
- Nếu bấm link mà báo "không thể kết nối" (ERR_CONNECTION_REFUSED), nghĩa là
  Tomcat chưa chạy hoặc đang khởi động dở, đợi vài giây rồi thử lại.
