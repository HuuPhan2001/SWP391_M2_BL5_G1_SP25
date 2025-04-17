 IF NOT EXISTS(SELECT * FROM sys.databases WHERE name = 'OnlineShop')
  BEGIN
    CREATE DATABASE [OnlineShop]


    END
    GO
       USE [OnlineShop]
    GO

CREATE TABLE [black_list] (
  [black_list_id] int IDENTITY(1, 1) PRIMARY KEY NOT NULL,
  [user_id] int DEFAULT NULL,
  [order_quantity] int DEFAULT NULL,
  [create_at] datetime DEFAULT (current_timestamp)
)
GO

CREATE TABLE [blog] (
  [id] int IDENTITY(1, 1) PRIMARY KEY NOT NULL,
  [user_id] int NOT NULL,
  [title] nvarchar(255) NOT NULL,
  [content] text NOT NULL,
  [status] int NOT NULL,
  [created_date] datetime NOT NULL DEFAULT (current_timestamp)
)
GO

CREATE TABLE [cart] (
  [cart_id] int IDENTITY(1, 1) PRIMARY KEY NOT NULL,
  [user_id] int DEFAULT NULL,
  [create_at] datetime DEFAULT (current_timestamp),
  [update_at] datetime DEFAULT (current_timestamp)
)
GO

CREATE TABLE [cart_item] (
  [cart_item_id] int IDENTITY(1, 1) PRIMARY KEY NOT NULL,
  [cart_id] int DEFAULT NULL,
  [product_id] int DEFAULT NULL,
  [quantity] int DEFAULT NULL,
  [note] text DEFAULT NULL,
  [create_at] datetime DEFAULT (current_timestamp),
  [update_at] datetime DEFAULT (current_timestamp)
)
GO

CREATE TABLE [category_type] (
  [category_type_id] int IDENTITY(1, 1) PRIMARY KEY NOT NULL,
  [category_type_name] nvarchar(255) DEFAULT NULL,
  [category_type_desc] nvarchar(255) DEFAULT NULL,
  [status] int DEFAULT (1),
  [create_at] datetime DEFAULT (current_timestamp),
  [update_at] datetime DEFAULT (current_timestamp)
)

INSERT INTO [category_type] ([category_type_name], [category_type_desc], [status])
VALUES 
('Role', 'Defines user roles', 1);
GO

CREATE TABLE [category] (
  [category_id] int IDENTITY(1, 1) PRIMARY KEY NOT NULL,
  [category_type_id] int DEFAULT NULL,
  [category_name] nvarchar(255) DEFAULT NULL,
  [category_desc] nvarchar(255) DEFAULT NULL,
  [parent] int DEFAULT NULL,
  [category_banner] varchar(255) DEFAULT NULL,
  [status] int DEFAULT (1),
  [create_at] datetime DEFAULT (current_timestamp),
  [update_at] datetime DEFAULT (current_timestamp)
)

INSERT INTO [category] ([category_type_id], [category_name], [category_desc], [status])
VALUES
(1, 'Admin', 'Administrator with full access to the system', 1),
(1, 'Staff', 'Staff manage stuff in online shop', 1),
(1, 'Customer', 'Regular user who buys products', 1);
GO

CREATE TABLE [user] (
  [user_id] int IDENTITY(1, 1) PRIMARY KEY NOT NULL,
  [user_name] varchar(255) DEFAULT NULL,
  [user_email] varchar(255) DEFAULT NULL,
  [user_password] varchar(255) DEFAULT NULL,
  [user_full_name] nvarchar(255) DEFAULT NULL,
  [user_avatar] varchar(MAX) DEFAULT NULL,
  [phone] varchar(11) DEFAULT NULL,
  [identification_number] varchar(20) DEFAULT NULL,
  [forgot_password_code] varchar(255) DEFAULT NULL,
  [address] varchar(255) DEFAULT NULL,
  [role_id] int DEFAULT NULL,
  [status] int DEFAULT (1),
  [create_at] datetime DEFAULT (current_timestamp),
  [update_at] datetime DEFAULT (current_timestamp)
)

INSERT INTO [user] ([user_name], [user_email], [user_password], [user_full_name], [role_id], [status])
VALUES 
('admin', 'admin@gmail.com', '123456', 'Admin', 1, 1),
('customer', 'customer@gmail.com', '123456', N'Nguyễn Văn A', 3, 1),
('staff', 'staff@gmail.com', '123456', 'Staff B', 2, 1);

GO

CREATE TABLE [user_feedback] (
  [feedback_id] int IDENTITY(1, 1) PRIMARY KEY NOT NULL,
  [feedback_rating] float DEFAULT NULL,
  [feedback_comment] nvarchar(255) DEFAULT NULL,
  [user_id] int DEFAULT NULL,
  [product_id] int DEFAULT NULL,
  [create_at] datetime DEFAULT (current_timestamp),
  [update_at] datetime DEFAULT (current_timestamp)
)
GO

CREATE TABLE [user_report] (
  [report_id] int IDENTITY(1, 1) PRIMARY KEY NOT NULL,
  [report_content] text DEFAULT NULL,
  [user_id] int DEFAULT NULL,
  [create_at] datetime DEFAULT (current_timestamp),
  [update_at] datetime DEFAULT (current_timestamp),
  [report_response] text DEFAULT NULL,
  [type_id] int DEFAULT NULL,
  [status] int DEFAULT (1)
)
GO

CREATE TABLE [user_report_image] (
  [report_image_id] int IDENTITY(1, 1) PRIMARY KEY NOT NULL,
  [report_image_content] text DEFAULT NULL,
  [report_id] int DEFAULT NULL,
  [create_at] datetime DEFAULT (current_timestamp),
  [update_at] datetime DEFAULT (current_timestamp)
)
GO

CREATE TABLE [feedback_image] (
  [feedback_image_id] int IDENTITY(1, 1) PRIMARY KEY NOT NULL,
  [feedback_image_content] text DEFAULT NULL,
  [feedback_id] int DEFAULT NULL,
  [create_at] datetime DEFAULT (current_timestamp),
  [update_at] datetime DEFAULT (current_timestamp)
)
GO

CREATE TABLE [order] (
  [order_id] int IDENTITY(1, 1) PRIMARY KEY NOT NULL,
  [user_id] int DEFAULT NULL,
  [shop_id] int DEFAULT NULL,
  [order_total] int DEFAULT NULL,
  [order_status] varchar(255) DEFAULT NULL,
  [payment_status] int DEFAULT (1) NOT NULL,
  [address] nvarchar(255) DEFAULT NULL,
  [district] nvarchar(255) DEFAULT NULL,
  [ward] nvarchar(255) DEFAULT NULL,
  [phone_receiver] varchar(255) DEFAULT NULL,
  [name_receiver] nvarchar(255) DEFAULT NULL,
  [ship_cost] int NOT NULL,
  [payment_method_id] int NOT NULL,
  [create_at] datetime DEFAULT (current_timestamp),
  [update_at] datetime DEFAULT (current_timestamp),
  [province] nvarchar(255) DEFAULT NULL,
  [order_code] varchar(45) NOT NULL,
  [discount] int DEFAULT NULL
)
GO

CREATE TABLE [order_detail] (
  [order_detail_id] int IDENTITY(1, 1) PRIMARY KEY NOT NULL,
  [order_id] int DEFAULT NULL,
  [product_id] int DEFAULT NULL,
  [quantity] int DEFAULT NULL,
  [unit_price] int DEFAULT NULL,
  [note] text DEFAULT NULL,
  [create_at] datetime DEFAULT (current_timestamp),
  [update_at] datetime DEFAULT (current_timestamp),
  [product_option_id] int DEFAULT NULL,
  [is_feedback] int DEFAULT (0)
)
GO

CREATE TABLE [payment] (
  [payment_id] int IDENTITY(1, 1) PRIMARY KEY NOT NULL,
  [payment_method_id] int DEFAULT NULL,
  [status] varchar(255) DEFAULT NULL,
  [amount] int DEFAULT NULL,
  [order_id] int DEFAULT NULL,
  [create_at] datetime DEFAULT (current_timestamp),
  [update_at] datetime DEFAULT (current_timestamp)
)
GO

CREATE TABLE [payment_method] (
  [payment_method_id] int IDENTITY(1, 1) PRIMARY KEY NOT NULL,
  [payment_method_name] nvarchar(255) DEFAULT NULL,
  [create_at] datetime DEFAULT (current_timestamp),
  [update_at] datetime DEFAULT (current_timestamp)
)
GO

CREATE TABLE [product] (
  [product_id] int IDENTITY(1, 1) PRIMARY KEY NOT NULL,
  [product_name] nvarchar(255) DEFAULT NULL,
  [product_avatar] text DEFAULT NULL,
  [product_desc] text DEFAULT NULL,
  [product_price] int DEFAULT NULL,
  [product_quantity] int DEFAULT NULL,
  [status] int NOT NULL,
  [create_at] datetime DEFAULT (current_timestamp),
  [update_at] datetime DEFAULT (current_timestamp),
  [weight] decimal(5,1) DEFAULT NULL,
  [length] decimal(5,1) DEFAULT NULL,
  [width] decimal(5,1) DEFAULT NULL,
  [height] decimal(5,1) DEFAULT NULL
)
GO

CREATE TABLE [product_category] (
  [product_category_id] int IDENTITY(1, 1) PRIMARY KEY NOT NULL,
  [product_id] int DEFAULT NULL,
  [category_id] int DEFAULT NULL
)
GO

CREATE TABLE [product_image] (
  [product_image_id] int IDENTITY(1, 1) PRIMARY KEY NOT NULL,
  [product_id] int DEFAULT NULL,
  [product_image] text DEFAULT NULL,
  [create_at] datetime DEFAULT (current_timestamp),
  [update_at] datetime DEFAULT (current_timestamp)
)
GO

CREATE TABLE [comment] (
  [id] int IDENTITY(1, 1) PRIMARY KEY NOT NULL,
  [comment_content] text NOT NULL,
  [create_at] datetime DEFAULT (current_timestamp),
  [update_at] datetime DEFAULT (current_timestamp),
  [user_id] int DEFAULT NULL,
  [status] int DEFAULT (1)
)
GO

CREATE TABLE [comment_image] (
  [comment_image_id] int IDENTITY(1, 1) PRIMARY KEY NOT NULL,
  [comment_image_content] text DEFAULT NULL,
  [comment_id] int DEFAULT NULL,
  [create_at] datetime DEFAULT (current_timestamp),
  [update_at] datetime DEFAULT (current_timestamp)
)
GO

CREATE TABLE [shop_transaction] (
  [id] int IDENTITY(1, 1) PRIMARY KEY NOT NULL,
  [user_id] int DEFAULT NULL,
  [order_id] int DEFAULT NULL,
  [amount] int DEFAULT NULL,
  [type] int DEFAULT NULL,
  [is_admin_transaction] int DEFAULT (0),
  [is_paid_commission] int DEFAULT (0),
  [created_at] datetime DEFAULT (current_timestamp),
  [updated_at] datetime DEFAULT (current_timestamp),
  [net_amount] int DEFAULT NULL,
  [description] text DEFAULT NULL
)
GO

CREATE TABLE [voucher] (
  [voucher_id] int IDENTITY(1, 1) PRIMARY KEY NOT NULL,
  [code] varchar(50) NOT NULL,
  [description] nvarchar(max) DEFAULT NULL,
  [discount_amount] int DEFAULT NULL,
  [min_order_amount] int DEFAULT NULL,
  [start_date] datetime NOT NULL,
  [end_date] datetime NOT NULL,
  [quantity] int DEFAULT NULL,
  [product_id] int DEFAULT NULL,
  [status] int DEFAULT (1),
  [created_at] datetime DEFAULT (current_timestamp),
  [updated_at] datetime DEFAULT (current_timestamp)
)
INSERT INTO [voucher] ([code], [description], [discount_amount], [min_order_amount], [start_date], [end_date], [quantity]) VALUES
('Summer', 'giảm giá toàn bộ sản phẩm', 49000, 200000, '2025-04-01 00:00:00', '2025-06-30 00:00:00', 500),
('Summer2', 'giảm giá toàn bộ sản phẩm', 50000, 2000000, '2025-04-15 00:00:00', '2025-04-30 00:00:00', 500),
('34', '', 49000, 20000, '2025-04-01 00:00:00', '2025-04-29 00:00:00', 12),
('123', '', 50000, 30000, '2025-04-15 00:00:00', '2025-04-29 00:00:00', 12),
('1234', '123', 123, 123, '2025-04-05 00:00:00', '2025-04-29 00:00:00', 123);
GO

CREATE TABLE [wishlist] (
  [wishlist_id] int IDENTITY(1, 1) PRIMARY KEY NOT NULL,
  [user_id] int DEFAULT NULL,
  [create_at] datetime DEFAULT (current_timestamp)
)
GO

CREATE TABLE [wishlist_item] (
  [wishlist_item_id] int IDENTITY(1, 1) PRIMARY KEY NOT NULL,
  [wishlist_id] int DEFAULT NULL,
  [product_id] int DEFAULT NULL,
  [create_at] datetime DEFAULT (current_timestamp)
)
GO

ALTER TABLE [black_list] ADD CONSTRAINT [black_list_ibfk_1] FOREIGN KEY ([user_id]) REFERENCES [user] ([user_id])
GO

ALTER TABLE [cart] ADD CONSTRAINT [cart_ibfk_1] FOREIGN KEY ([user_id]) REFERENCES [user] ([user_id])
GO

ALTER TABLE [cart_item] ADD CONSTRAINT [cart_item_ibfk_1] FOREIGN KEY ([cart_id]) REFERENCES [cart] ([cart_id])
GO

ALTER TABLE [cart_item] ADD CONSTRAINT [cart_item_ibfk_2] FOREIGN KEY ([product_id]) REFERENCES [product] ([product_id])
GO

ALTER TABLE [user_feedback] ADD CONSTRAINT [user_feedback_ibfk_1] FOREIGN KEY ([user_id]) REFERENCES [user] ([user_id])
GO

ALTER TABLE [user_feedback] ADD CONSTRAINT [user_feedback_ibfk_2] FOREIGN KEY ([product_id]) REFERENCES [product] ([product_id])
GO

ALTER TABLE [user_report] ADD CONSTRAINT [FK_user_report_category] FOREIGN KEY ([type_id]) REFERENCES [category] ([category_id])
GO

ALTER TABLE [user_report] ADD CONSTRAINT [user_report_ibfk_1] FOREIGN KEY ([user_id]) REFERENCES [user] ([user_id])
GO

ALTER TABLE [user_report_image] ADD CONSTRAINT [user_report_image_ibfk_1] FOREIGN KEY ([report_id]) REFERENCES [user_report] ([report_id])
GO

ALTER TABLE [feedback_image] ADD CONSTRAINT [feedback_image_ibfk_1] FOREIGN KEY ([feedback_id]) REFERENCES [user_feedback] ([feedback_id])
GO

ALTER TABLE [order] ADD CONSTRAINT [order_ibfk_1] FOREIGN KEY ([user_id]) REFERENCES [user] ([user_id])
GO

ALTER TABLE [order] ADD CONSTRAINT [orrder_payment] FOREIGN KEY ([payment_method_id]) REFERENCES [payment_method] ([payment_method_id])
GO

ALTER TABLE [order_detail] ADD CONSTRAINT [order_detail_ibfk_1] FOREIGN KEY ([order_id]) REFERENCES [order] ([order_id])
GO

ALTER TABLE [order_detail] ADD CONSTRAINT [order_detail_ibfk_2] FOREIGN KEY ([product_id]) REFERENCES [product] ([product_id])
GO

ALTER TABLE [payment] ADD CONSTRAINT [payment_ibfk_1] FOREIGN KEY ([payment_method_id]) REFERENCES [payment_method] ([payment_method_id])
GO

ALTER TABLE [payment] ADD CONSTRAINT [payment_ibfk_2] FOREIGN KEY ([order_id]) REFERENCES [order] ([order_id])
GO

ALTER TABLE [product_category] ADD CONSTRAINT [product_category_ibfk_1] FOREIGN KEY ([product_id]) REFERENCES [product] ([product_id])
GO

ALTER TABLE [product_category] ADD CONSTRAINT [product_category_ibfk_2] FOREIGN KEY ([category_id]) REFERENCES [category] ([category_id])
GO

ALTER TABLE [product_image] ADD CONSTRAINT [product_image_ibfk_1] FOREIGN KEY ([product_id]) REFERENCES [product] ([product_id])
GO

ALTER TABLE [comment_image] ADD CONSTRAINT [FK_comment_image_comment] FOREIGN KEY ([comment_id]) REFERENCES [comment] ([id])
GO

ALTER TABLE [shop_transaction] ADD CONSTRAINT [shop_transaction_ibfk_1] FOREIGN KEY ([order_id]) REFERENCES [order] ([order_id])
GO

ALTER TABLE [shop_transaction] ADD CONSTRAINT [shop_transaction_ibfk_3] FOREIGN KEY ([user_id]) REFERENCES [user] ([user_id])
GO

ALTER TABLE [wishlist] ADD CONSTRAINT [wishlist_ibfk_1] FOREIGN KEY ([user_id]) REFERENCES [user] ([user_id])
GO

ALTER TABLE [wishlist_item] ADD CONSTRAINT [wishlist_item_ibfk_1] FOREIGN KEY ([wishlist_id]) REFERENCES [wishlist] ([wishlist_id])
GO

ALTER TABLE [wishlist_item] ADD CONSTRAINT [wishlist_item_ibfk_2] FOREIGN KEY ([product_id]) REFERENCES [product] ([product_id])
GO

ALTER TABLE [category] ADD CONSTRAINT [category_type_ibfk_1] FOREIGN KEY ([category_type_id]) REFERENCES [category_type] ([category_type_id])
GO

ALTER TABLE [user] ADD CONSTRAINT FK_user_category FOREIGN KEY ([role_id]) REFERENCES [category]([category_id])
GO

ALTER TABLE [voucher] ADD CONSTRAINT [FK_product_voucher] FOREIGN KEY ([product_id]) REFERENCES [product] ([product_id])
GO

ALTER TABLE [blog] ADD CONSTRAINT [FK_user_blog] FOREIGN KEY ([user_id]) REFERENCES [user] ([user_id])
GO
