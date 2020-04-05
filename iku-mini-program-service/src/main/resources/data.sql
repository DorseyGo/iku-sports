INSERT INTO category (display_name, `name`, icon, sequence) VALUES ('篮球', 'basketball', NULL, 0), ('网球',
'tennis', NULL, 1), ('游泳', 'swimming', NULL, 2);


INSERT INTO activity (image, title) VALUES('/images/basketball_2x.png', '篮球5折'), ('/images/swimming_2x.png', '游泳7折'),
('/images/tennis_2x.png', '网球6折');

INSERT INTO course (name, background_img, joiner, category_id) VALUES ('初级篮球技巧', '/images/basketball_2x.png', 1200000, 1),
('中级篮球技巧', '/images/basketball_2x.png', 1200000, 1), ('高级篮球技巧', '/images/basketball_2x.png', 1400000, 1);

INSERT INTO coach (name, title, heading_img_url, gender, age, nationality, level, introduce)
  VALUES ('De', '国际教练 中国女篮教练', '/images/WechatIMG769', 'M', 39, 'SWY', 4, ''), ('Jan', '国际教练 中国女篮教练', '/images/WechatIMG768', 'M', 39, 'SWY', 4, '');

INSERT INTO article (title, cover, author, category_id) VALUES ('老许二三事', '/images/article_2x.png', 'LaoXU', 1);