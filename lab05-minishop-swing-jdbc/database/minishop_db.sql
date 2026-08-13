-- cập nhật cơ sở dữ liệu minishop_db.sql
CREATE TABLE danh_muc (
  ma_dm INT AUTO_INCREMENT PRIMARY KEY,
  ten_dm VARCHAR(100) NOT NULL
);

ALTER TABLE san_pham
ADD ma_dm INT NULL,
ADD FOREIGN KEY (ma_dm) REFERENCES danh_muc(ma_dm);

INSERT INTO danh_muc(ten_dm) VALUES
('Thiet bi ngoai vi'), ('Linh kien luu tru'), ('Am thanh');

CREATE TABLE tai_khoan (
  username VARCHAR(50) PRIMARY KEY,
  password VARCHAR(100) NOT NULL,
  ho_ten VARCHAR(100) NOT NULL,
  vai_tro VARCHAR(20) NOT NULL
);

INSERT INTO tai_khoan(username, password, ho_ten, vai_tro) VALUES
('admin', 'admin123', 'Quan tri vien', 'ADMIN'),
('nhanvien1', '123456', 'Nguyen Van Nhan Vien', 'NHANVIEN'),
('ketoan1', '123456', 'Tran Thi Ke Toan', 'KETOAN');*