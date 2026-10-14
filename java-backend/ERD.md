🧠 NGUYÊN TẮC THIẾT KẾ

Camunda lo workflow

DB nghiệp vụ lo dữ liệu công văn

Liên kết bằng:

business_key = cong_van_id

1️⃣ cong_van (BẢNG TRUNG TÂM)
cong_van

Field	Type	Ý nghĩa
id	BIGINT (PK)	ID công văn
ma_cong_van	VARCHAR	Số / ký hiệu
loai_cong_van	VARCHAR	DEN / DI
trich_yeu	TEXT	Trích yếu
ngay_ban_hanh	DATE	Ngày ban hành
do_khan	VARCHAR	Thường / Khẩn
do_mat	VARCHAR	Thường / Mật
trang_thai	VARCHAR	Draft / Processing / Done
nguoi_tao	VARCHAR	User tạo
phong_ban_id	BIGINT	Phòng ban
process_instance_id	VARCHAR	Camunda process id
created_at	TIMESTAMP	Tạo
updated_at	TIMESTAMP	Cập nhật
2️⃣ cong_van_den
cong_van_den

Field	Type	Ý nghĩa
id	BIGINT (PK)	FK → cong_van
so_den	VARCHAR	Số đến
ngay_den	DATE	Ngày đến
noi_gui	VARCHAR	Nơi gửi
nguoi_ky	VARCHAR	Người ký
han_xu_ly	DATE	Deadline
3️⃣ cong_van_di
cong_van_di

Field	Type	Ý nghĩa
id	BIGINT (PK)	FK → cong_van
so_di	VARCHAR	Số đi
noi_nhan	TEXT	Nơi nhận
nguoi_ky	VARCHAR	Người ký
ngay_phat_hanh	DATE	Phát hành
4️⃣ tep_dinh_kem (FILE ĐÍNH KÈM)
tep_dinh_kem

Field	Type	Ý nghĩa
id	BIGINT (PK)
cong_van_id	BIGINT (FK)
ten_file	VARCHAR
duong_dan	VARCHAR
loai_file	VARCHAR
kich_thuoc	BIGINT
created_at	TIMESTAMP
5️⃣ xu_ly_cong_van (LỊCH SỬ XỬ LÝ)
xu_ly_cong_van

Field	Type	Ý nghĩa
id	BIGINT (PK)
cong_van_id	BIGINT
task_id	VARCHAR	Camunda task
nguoi_xu_ly	VARCHAR
hanh_dong	VARCHAR	APPROVE / REJECT
y_kien	TEXT
thoi_gian	TIMESTAMP
6️⃣ phan_cong_xu_ly
phan_cong_xu_ly

Field	Type	Ý nghĩa
id	BIGINT (PK)
cong_van_id	BIGINT
nguoi_nhan	VARCHAR
vai_tro	VARCHAR	Chủ trì / Phối hợp
han_xu_ly	DATE
7️⃣ so_cong_van (ĐÁNH SỐ)
so_cong_van

Field	Type	Ý nghĩa
id	BIGINT
nam	INT
loai	VARCHAR	DEN / DI
so_hien_tai	INT
8️⃣ nhat_ky_he_thong (AUDIT)
nhat_ky_he_thong

Field	Type	Ý nghĩa
id	BIGINT
nguoi_thuc_hien	VARCHAR
hanh_dong	VARCHAR
doi_tuong	VARCHAR
doi_tuong_id	BIGINT
thoi_gian	TIMESTAMP
9️⃣ (TÙY CHỌN) chu_ky_so
chu_ky_so

Field	Type
id	BIGINT
cong_van_id	BIGINT
nguoi_ky	VARCHAR
thoi_gian_ky	TIMESTAMP
file_da_ky	VARCHAR
🔗 QUAN HỆ TỔNG QUÁT
cong_van
├── cong_van_den
├── cong_van_di
├── tep_dinh_kem
├── xu_ly_cong_van
├── phan_cong_xu_ly
└── chu_ky_so



KẾT LUẬN

Đây là FULL TABLE CHUẨN NGHIỆP VỤ CÔNG VĂN

Áp dụng:

Nhà nước (NĐ 30/2020)

Doanh nghiệp

Tách bạch workflow (Camunda) & business data
