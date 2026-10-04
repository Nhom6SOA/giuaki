CREATE DATABASE IF NOT EXISTS qlda
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE qlda;

CREATE TABLE IF NOT EXISTS student (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_code VARCHAR(20) NOT NULL UNIQUE,
    full_name VARCHAR(100) NOT NULL,
    class_name VARCHAR(50),
    email VARCHAR(100),
    phone VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS topic (
    id INT AUTO_INCREMENT PRIMARY KEY,
    topic_code VARCHAR(20) NOT NULL UNIQUE,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    supervisor VARCHAR(100),
    max_students INT DEFAULT 1,
    status VARCHAR(20) DEFAULT 'OPEN'
);
CREATE TABLE IF NOT EXISTS topic_registration (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    topic_id INT,
    registered_at DATE,
    status VARCHAR(20),
    note TEXT,

    FOREIGN KEY (student_id)
        REFERENCES student(id),

    FOREIGN KEY (topic_id)
        REFERENCES topic(id)
);

INSERT IGNORE INTO student
(student_code, full_name, class_name, email, phone)
VALUES
('SV001', 'Nguyễn Văn An', 'CNTT01', 'an.nv@example.com', '0901000001'),
('SV002', 'Trần Thị Bình', 'CNTT01', 'binh.tt@example.com', '0901000002'),
('SV003', 'Lê Minh Châu', 'CNTT02', 'chau.lm@example.com', '0901000003'),
('SV004', 'Phạm Quốc Đạt', 'CNTT02', 'dat.pq@example.com', '0901000004'),
('SV005', 'Hoàng Thu Hà', 'CNTT03', 'ha.ht@example.com', '0901000005'),
('SV006', 'Võ Thanh Hải', 'CNTT03', 'hai.vt@example.com', '0901000006'),
('SV007', 'Đặng Mỹ Linh', 'KTPM01', 'linh.dm@example.com', '0901000007'),
('SV008', 'Bùi Quang Minh', 'KTPM01', 'minh.bq@example.com', '0901000008'),
('SV009', 'Ngô Thị Ngọc', 'KTPM02', 'ngoc.nt@example.com', '0901000009'),
('SV010', 'Đỗ Hữu Phúc', 'KTPM02', 'phuc.dh@example.com', '0901000010'),
('SV011', 'Lý Thùy Trang', 'HTTT01', 'trang.lt@example.com', '0901000011'),
('SV012', 'Trịnh Văn Tuấn', 'HTTT01', 'tuan.tv@example.com', '0901000012');

INSERT IGNORE INTO topic
(topic_code, title, description, supervisor, max_students, status)
VALUES
('DT001', 'Hệ thống quản lý thư viện', 'Web quản lý mượn trả sách, độc giả', 'ThS. Phạm Quốc Dũng', 2, 'OPEN'),
('DT002', 'Ứng dụng đặt lịch khám bệnh', 'Đặt lịch và quản lý hồ sơ bệnh nhân', 'TS. Hoàng Thu Hà', 1, 'OPEN'),
('DT003', 'Website bán hàng trực tuyến', 'Giỏ hàng, thanh toán, quản lý đơn hàng', 'ThS. Nguyễn Văn Hải', 2, 'OPEN'),
('DT004', 'Hệ thống quản lý học sinh', 'Quản lý điểm, hạnh kiểm, thời khóa biểu', 'TS. Lê Thị Mai', 1, 'OPEN'),
('DT005', 'Ứng dụng quản lý chi tiêu cá nhân', 'Ghi chép thu chi, thống kê biểu đồ', 'ThS. Trần Minh Khoa', 2, 'OPEN'),
('DT006', 'Hệ thống đặt phòng khách sạn', 'Tìm phòng, đặt phòng, quản lý lễ tân', 'ThS. Phạm Quốc Dũng', 1, 'OPEN'),
('DT007', 'Nền tảng học trực tuyến', 'Khóa học, bài giảng video, làm bài kiểm tra', 'TS. Võ Hoàng Long', 2, 'OPEN'),
('DT008', 'Hệ thống quản lý nhân sự', 'Hồ sơ nhân viên, chấm công, tính lương', 'TS. Lê Thị Mai', 1, 'OPEN'),
('DT009', 'Ứng dụng đặt đồ ăn', 'Đặt món, theo dõi đơn, quản lý nhà hàng', 'ThS. Nguyễn Văn Hải', 2, 'OPEN'),
('DT010', 'Hệ thống quản lý kho hàng', 'Nhập xuất tồn, cảnh báo hết hàng', 'ThS. Trần Minh Khoa', 1, 'OPEN'),
('DT011', 'Hệ thống bầu cử trực tuyến', 'Đề tài đã đủ nhóm, đóng đăng ký', 'TS. Võ Hoàng Long', 1, 'CLOSED'),
('DT012', 'Chatbot hỗ trợ tuyển sinh', 'Trả lời tự động câu hỏi của thí sinh', 'TS. Hoàng Thu Hà', 2, 'OPEN');

INSERT IGNORE INTO topic_registration
(student_id, topic_id, registered_at, status, note)
VALUES

((SELECT id FROM student WHERE student_code='SV001'),
 (SELECT id FROM topic WHERE topic_code='DT001'),
 CURDATE(), 'APPROVED', 'Đã được GVHD đồng ý'),

((SELECT id FROM student WHERE student_code='SV003'),
 (SELECT id FROM topic WHERE topic_code='DT001'),
 CURDATE(), 'APPROVED', 'Làm nhóm với SV001'),

((SELECT id FROM student WHERE student_code='SV002'),
 (SELECT id FROM topic WHERE topic_code='DT002'),
 CURDATE(), 'APPROVED', 'Đề tài đã đủ 1/1 sinh viên'),

((SELECT id FROM student WHERE student_code='SV004'),
 (SELECT id FROM topic WHERE topic_code='DT003'),
 CURDATE(), 'APPROVED', NULL),

((SELECT id FROM student WHERE student_code='SV005'),
 (SELECT id FROM topic WHERE topic_code='DT003'),
 CURDATE(), 'PENDING', 'Chờ giảng viên xem xét'),

((SELECT id FROM student WHERE student_code='SV002'),
 (SELECT id FROM topic WHERE topic_code='DT003'),
 CURDATE(), 'REJECTED', 'Sinh viên đã có đề tài khác'),

((SELECT id FROM student WHERE student_code='SV006'),
 (SELECT id FROM topic WHERE topic_code='DT004'),
 CURDATE(), 'APPROVED', 'Đề tài đã đủ 1/1 sinh viên'),

((SELECT id FROM student WHERE student_code='SV007'),
 (SELECT id FROM topic WHERE topic_code='DT005'),
 CURDATE(), 'PENDING', NULL),

((SELECT id FROM student WHERE student_code='SV005'),
 (SELECT id FROM topic WHERE topic_code='DT005'),
 CURDATE(), 'PENDING', 'Đăng ký dự phòng'),

((SELECT id FROM student WHERE student_code='SV008'),
 (SELECT id FROM topic WHERE topic_code='DT006'),
 CURDATE(), 'REJECTED', 'Chưa đủ điều kiện'),

((SELECT id FROM student WHERE student_code='SV009'),
 (SELECT id FROM topic WHERE topic_code='DT007'),
 CURDATE(), 'APPROVED', NULL),

((SELECT id FROM student WHERE student_code='SV010'),
 (SELECT id FROM topic WHERE topic_code='DT007'),
 CURDATE(), 'APPROVED', 'Đề tài đã đủ 2/2 sinh viên'),

((SELECT id FROM student WHERE student_code='SV011'),
 (SELECT id FROM topic WHERE topic_code='DT008'),
 CURDATE(), 'PENDING', NULL),

((SELECT id FROM student WHERE student_code='SV012'),
 (SELECT id FROM topic WHERE topic_code='DT009'),
 CURDATE(), 'PENDING', 'Em có kinh nghiệm làm app di động'),

((SELECT id FROM student WHERE student_code='SV008'),
 (SELECT id FROM topic WHERE topic_code='DT010'),
 CURDATE(), 'PENDING', NULL);

SELECT * FROM student;
SELECT * FROM topic;
SELECT * FROM topic_registration;

-- DEM SO LUONG BAN GHI
SELECT COUNT(*) AS total_students FROM student;
SELECT COUNT(*) AS total_topics FROM topic;
SELECT COUNT(*) AS total_registrations FROM topic_registration;
