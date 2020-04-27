INSERT INTO category (display_name, `name`, icon, sequence) VALUES ('篮球', 'basketball', NULL, 0), ('网球',
'tennis', NULL, 1), ('游泳', 'swimming', NULL, 2);


INSERT INTO activity (image, title) VALUES('/images/basketball_2x.png', '篮球5折'), ('/images/swimming_2x.png', '游泳7折'),
('/images/tennis_2x.png', '网球6折');

INSERT INTO course (name, background_img, joiner, category_id) VALUES ('初级篮球技巧', '/images/basketball_2x.png', 1200000, 1),
('中级篮球技巧', '/images/basketball_2x.png', 1200000, 1), ('高级篮球技巧', '/images/basketball_2x.png', 1400000, 1);

INSERT INTO coach (name, title, heading_img_url, gender, age, nationality, level, introduce)
  VALUES ('De', '国际教练 中国女篮教练', '/images/WechatIMG769', 'M', 39, 'SWY', 4, ''), ('Jan', '国际教练 中国女篮教练', '/images/WechatIMG768', 'M', 39, 'SWY', 4, '');

INSERT INTO article (title, cover, author, category_id) VALUES ('老许二三事', '/images/article_2x.png', 'LaoXU', 1);

INSERT INTO CLASS(title,chapter,cover,video_url,content,watches,course_id,coach_id)
VALUES('zz',1,'zz','zz','zz',123,1,1);

INSERT INTO class(title,chapter,cover,video_url,content,watches,course_id,coach_id)
values ('aa',1,'/images/basketball_2x.png',null,'aa',1,1,1),
('bb',1,'/images/basketball_2x.png',null,'bb',1,1,2),
('cc',1,'/images/basketball_2x.png',null,'cc',1,1,2),
('dd',1,'/images/basketball_2x.png',null,'aa',1,1,3),
('ee',1,'/images/basketball_2x.png',null,'aa',1,1,4),
('ff',1,'/images/basketball_2x.png',null,'aa',1,1,5),
('gg',1,'/images/basketball_2x.png',null,'aa',1,1,6),
('qq',1,'/images/basketball_2x.png',null,'aa',1,1,7),
('ww',1,'/images/basketball_2x.png',null,'aa',1,1,8),
('ee',1,'/images/basketball_2x.png',null,'aa',1,1,9),
('rr',1,'/images/basketball_2x.png',null,'aa',1,1,11),
('tt',1,'/images/basketball_2x.png',null,'aa',1,1,12),
('yy',1,'/images/basketball_2x.png',null,'aa',1,1,13);



SELECT t.id, t.title, t.cover, t.content, t.watches, t.course_id FROM class T WHERE (course_id=1) ORDER BY T.create_time desc,T.watches desc limit 1,10



SELECT IFNULL(count(1),0) FROM collect WHERE (student_id = 1 AND collect_type = 1)