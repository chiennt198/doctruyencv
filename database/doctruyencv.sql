/*
Navicat MySQL Data Transfer

Source Server         : server11
Source Server Version : 50727
Source Host           : 192.168.1.11:3306
Source Database       : doctruyencv

Target Server Type    : MYSQL
Target Server Version : 50727
File Encoding         : 65001

Date: 2020-11-12 16:44:43
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for M_ADMIN
-- ----------------------------
DROP TABLE IF EXISTS `M_ADMIN`;
CREATE TABLE `M_ADMIN` (
  `USER_ID` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `NAME` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `EMAIL` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PASSWORD` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of M_ADMIN
-- ----------------------------

-- ----------------------------
-- Table structure for M_CATEGORY
-- ----------------------------
DROP TABLE IF EXISTS `M_CATEGORY`;
CREATE TABLE `M_CATEGORY` (
  `CATEGORY_ID` int(10) NOT NULL,
  `CATEGORY_NAME` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`CATEGORY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of M_CATEGORY
-- ----------------------------
INSERT INTO `M_CATEGORY` VALUES ('1', 'Tiên Hiệp');
INSERT INTO `M_CATEGORY` VALUES ('2', 'Huyền Huyễn');
INSERT INTO `M_CATEGORY` VALUES ('3', 'Khoa Huyễn');
INSERT INTO `M_CATEGORY` VALUES ('4', 'Võng Du');
INSERT INTO `M_CATEGORY` VALUES ('5', 'Đô Thị');
INSERT INTO `M_CATEGORY` VALUES ('6', 'Đồng Nhân');
INSERT INTO `M_CATEGORY` VALUES ('7', 'Lịch Sử');
INSERT INTO `M_CATEGORY` VALUES ('8', 'Cạnh Kỹ');
INSERT INTO `M_CATEGORY` VALUES ('9', 'Huyền Nghi');
INSERT INTO `M_CATEGORY` VALUES ('10', 'Kiếm Hiệp');
INSERT INTO `M_CATEGORY` VALUES ('11', 'Kỳ Ảo');

-- ----------------------------
-- Table structure for M_WIDE
-- ----------------------------
DROP TABLE IF EXISTS `M_WIDE`;
CREATE TABLE `M_WIDE` (
  `IDX` int(4) NOT NULL,
  `CD` int(2) NOT NULL,
  `NAME` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DESCRIPTION` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of M_WIDE
-- ----------------------------
INSERT INTO `M_WIDE` VALUES ('1', '1', 'Chưa Hoàn Thành', null);
INSERT INTO `M_WIDE` VALUES ('1', '2', 'Hoàn Thành', null);

-- ----------------------------
-- Table structure for T_AUTHOR
-- ----------------------------
DROP TABLE IF EXISTS `T_AUTHOR`;
CREATE TABLE `T_AUTHOR` (
  `ID` int(11) NOT NULL,
  `NAME` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DESCRIPTION` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of T_AUTHOR
-- ----------------------------

-- ----------------------------
-- Table structure for T_CHAPTERS
-- ----------------------------
DROP TABLE IF EXISTS `T_CHAPTERS`;
CREATE TABLE `T_CHAPTERS` (
  `ID` int(11) NOT NULL,
  `STORY_ID` int(11) NOT NULL,
  `NAME` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CONTENT` longtext COLLATE utf8_unicode_ci,
  `SORT_KEY` int(11) DEFAULT NULL,
  `DELETE_FLG` date DEFAULT NULL,
  `INSERT_DATETIME` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_DATETIME` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`,`STORY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of T_CHAPTERS
-- ----------------------------
INSERT INTO `T_CHAPTERS` VALUES ('1', '1', 'Chương 1-1: Dư nghiệt kiếm lô (1)', 'Mùa thu năm Nguyên Vũ thứ mười một, một cơn mưa to hiếm thấy đổ xuống toàn bộ vùng Trường Lăng. Mây đen nặng như chì kèm theo sấm sét khủng khiếp làm cho cả vùng đô thành vương triều Đại Tần như là ma giới.\n\nTại bến cảng Vị Hà bên ngoài thành, vô số quan viên mặc quan phục màu đen và binh sĩ đứng thẳng như tượng, mặc cho gió quật mưa vùi. Tất cả đều như cây đinh sắt đóng xuống đất, không nhúc nhích chút nào.\n\nTrong cảnh sóng cao ngang trời, một chiếc thuyền lớn bọc sắt đột nhiên xuất hiện!\n\nLúc này, một tia chớp rạch ngang trời bổ xuống, ánh sáng lòa chiếu sáng chiếc thuyền lớn bọc sắt nặng nề.\n\nToàn bộ đám quan viên và quân sĩ đang đứng nghiêm đều biến sắc hoảng sợ.\n\nPhía đầu chiếc thuyền lớn bọc sắt là đầu một con rồng!\n\nĐầu rồng này lớn hơn chiếc xe ngựa, tựa như bị người ta cắt phăng. Hai đồng tử màu đỏ thẫm bốc lên sát ý điên cuồng, uy thế ngập trời còn hơn cả sóng to gió lớn nhường kia.\n\nKhông chờ thuyền lớn cập bờ, ba vị quan viên phi thân vượt qua hơn mấy chục thước mặt sông, như ba cái búa tạ hạ xuống boong thuyền.\n\nBa vị quan viên này càng thêm kinh hãi trong lòng bởi vì khắp nơi trên thuyền lớn lỗ chỗ những lỗ hổng và những đồ vật bị vỡ, nhìn thì biết chiếc thuyền đã trải qua không biết bao nhiêu trận chiến thảm liệt. Ba người nhìn quanh thì chỉ thấy một người quần áo tơi tả. Đó là một lão nhân có bộ dạng như lão bộc đang tựa vào mạn thuyền như một xác chết, nhìn không giống chút nào cái người mà để bọn họ đau khổ chờ đợi.\n\n“Hàn đại nhân, Dạ Ti Thủ đâu rồi?”\n\nBa vị quan viên thi lễ, cố nén sự kinh hãi bèn hỏi.\n\n“Không cần đa lễ, Dạ Ti Thủ đã đi tìm kiếm nơi ẩn nấp dư nghiệt Kiếm lô rồi.” Lão nhân có bộ dạng lão bộ khẽ khom lại đáp lễ. Đương lúc mưa như trút nên dù họ nói chuyện với nhau nhưng không rõ khuôn mặt lão nhân, chỉ thấy ánh mắt đó lãnh khốc, thâm trầm tản mát ra bá khí chấn nhiếp nhân tâm.\n\n“Dạ Ti Thủ đã đi?” Ba vị quan viên đồng thời chấn động, không nhịn được bèn quay đầu nhìn về phía thành đô.\n\nTrường Lăng đã vào buổi hoàng hôn, hư ảnh từng tòa vọng lâu cao vút như ẩn như hiện dưới cơn mưa bao phủ.\n\nCùng lúc đó, trên mặt sông phía Nam thành Trường Lăng đột nhiên xuất hiện một chiếc ô màu đen che mưa.\n\nNgười cầm chiếc ô đi trên sóng to gió cả như đi trên đất bằng, người này đi tới đầu một ngõ nhỏ ven bờ sông.\n\nCó sáu người cao thấp khác nhau cũng cầm chiếc ô đen. Sáu quan viên mặc hắc y bị cái ô che lấp nên không lộ ra khuôn mặt, họ lẳng lặng đứng ở bờ sông chờ người nọ.\n\nKhi người nọ lên bờ, sáu quan viên kia không có hành động gì lạ, không nói câu nào mà chỉ lẳng lặng đi ở sau lưng.\n\nTrong ngõ hẻm, có một trang viện bình thường, từ trung tâm chiếc ô đen che mưa dần dần tỏa ra sát khí.\n\nTiếng mưa tí tách lẫn với tiếng nhai đồ ăn.\n\nKhông chờ thuyền lớn cập bờ, ba vị quan viên phi thân vượt qua hơn mấy chục thước mặt sông, như ba cái búa tạ hạ xuống boong thuyền.\n\nBa vị quan viên này càng thêm kinh hãi trong lòng bởi vì khắp nơi trên thuyền lớn lỗ chỗ những lỗ hổng và những đồ vật bị vỡ, nhìn thì biết chiếc thuyền đã trải qua không biết bao nhiêu trận chiến thảm liệt. Ba người nhìn quanh thì chỉ thấy một người quần áo tơi tả. Đó là một lão nhân có bộ dạng như lão bộc đang tựa vào mạn thuyền như một xác chết, nhìn không giống chút nào cái người mà để bọn họ đau khổ chờ đợi.\n\n“Hàn đại nhân, Dạ Ti Thủ đâu rồi?”\n\nBa vị quan viên thi lễ, cố nén sự kinh hãi bèn hỏi.\n\n“Không cần đa lễ, Dạ Ti Thủ đã đi tìm kiếm nơi ẩn nấp dư nghiệt Kiếm lô rồi.” Lão nhân có bộ dạng lão bộ khẽ khom lại đáp lễ. Đương lúc mưa như trút nên dù họ nói chuyện với nhau nhưng không rõ khuôn mặt lão nhân, chỉ thấy ánh mắt đó lãnh khốc, thâm trầm tản mát ra bá khí chấn nhiếp nhân tâm.\n\n“Dạ Ti Thủ đã đi?” Ba vị quan viên đồng thời chấn động, không nhịn được bèn quay đầu nhìn về phía thành đô.\n\nTrường Lăng đã vào buổi hoàng hôn, hư ảnh từng tòa vọng lâu cao vút như ẩn như hiện dưới cơn mưa bao phủ.\n\nCùng lúc đó, trên mặt sông phía Nam thành Trường Lăng đột nhiên xuất hiện một chiếc ô màu đen che mưa.\n\nNgười cầm chiếc ô đi trên sóng to gió cả như đi trên đất bằng, người này đi tới đầu một ngõ nhỏ ven bờ sông.\n\nCó sáu người cao thấp khác nhau cũng cầm chiếc ô đen. Sáu quan viên mặc hắc y bị cái ô che lấp nên không lộ ra khuôn mặt, họ lẳng lặng đứng ở bờ sông chờ người nọ.\n\nKhi người nọ lên bờ, sáu quan viên kia không có hành động gì lạ, không nói câu nào mà chỉ lẳng lặng đi ở sau lưng.\n\nTrong ngõ hẻm, có một trang viện bình thường, từ trung tâm chiếc ô đen che mưa dần dần tỏa ra sát khí.\n\nTiếng mưa tí tách lẫn với tiếng nhai đồ ăn.\nMột người đàn ông trung niên mặc hắc y bằng vải thô kéo ống tay áo ở trong nội viện, dưới mái hiên đương ăn tối.\n\nNgười đàn ông này mặc hắc y cũ nát, đầu tóc rối bời được buộc lại bằng một cọng cỏ khô, đi một đôi giày vải với cái đế đã mòn vẹt, đôi bàn tay với những chiếc móng tay bẩn thỉu, khuôn mặt không có gì nổi bật nhìn chẳng khác gì phu khuân vác tầm thường.\n\nBữa tối của người này cũng hết sức bình thường, giản đơn, chỉ có một bát cơm bằng gạo thô, một đĩa rau, một đĩa đậu rang thế mà người đàn ông này ăn trông rất ngon lành, mỗi một miếng đều nhai từ tốn mấy chục lần mới nuốt vào bụng.\n\nKhi và miếng cơm cuối cùng, người đàn ông thò tay lấy chiếc gáo gỗ treo dưới mái hiên rồi đi tới chỗ chum nước múc một gáo nước trong. Người nọ uống một hơi cạn sạch rồi ợ một hơi thật thoải mái.\n\nTiếng ợ vang lên thì đồng thời với lúc chiếc ô đen che mưa dừng lại ở cánh cổng tiểu viện.\n\nTrên chiếc giày quan là chân váy tuyết trắng, từng lọn tóc bay múa, nét môi mỏng manh, cặp lông mi nhàn nhạt như núi mờ xa trong màn mưa.\n\nNhững bước chân tản bộ trên mặt sông trong cảnh sóng to gió lớn hóa ra là của một cô gái xinh đẹp rất có phong thái với vòng eo động lòng người.\n\nHạ chiếc ô đen xuống, mặc cho mưa thu xối lên mái tóc đen tuyền, cô gái nhẹ chân bước vào trang viên có người đàn ông trung niên. Cô gái nhẹ nhàng thi lễ rồi dịu dàng thốt lên:”Dạ Sách Lãnh ra mắt Triệu Thất tiên sinh.”\n\nNgười đàn ông trung niên hơi nhíu mày, chỉ với cái nhíu mày này mà bộ mặt góc cạnh của người nọ dường như trở nên sinh động hơn, một loại mị lực khó tả cũng bắt đầu tản mát ra.\n\n“Ta ở Trường Lăng ba năm mà mới lần đầu tiên gặp được Dạ Ti Thủ.”\n\nNgười nọ không hoàn lễ mà chỉ nhếch mép lên cười, ánh mắt lướt qua khuôn mặt cô gái nhìn về phía đường phố đan xen xa xa dưới màn mưa thu.\n\n“Nhìn mãi Trường Lăng thật sự không thú vị, cũng giống như kiếm hay tính cách người Tần vậy, bụng dạ thẳng thắn, ngang ran gang dọc ra dọc, bốn bề yên tĩnh, đến cả mặt đường mặt tường không phải màu xám cũng là màu đen, chẳng chút mỹ cảm. Bây giờ thấy phong độ và tư thái của Dạ Ti Thủ làm hai mắt ta sáng lên, dường như rất không hợp với Trường Lăng này.”\n\n‘To gan! Dư nghiệt Kiếm lô Triệu Trảm! Dạ Ti Thủ đích thân tới, ngươi còn không thúc thủ chịu trói mà vẫn còn dám đưa đẩy những lời lấy lòng!”\n\nMột tiếng quát chói tai lạnh như băng từ dưới một chiếc ô đen ở xa xa đột nhiên vọng tới.\n\nNgười này cố ý muốn để người đàn ông trung niên và cô gái mặc váy trắng nhìn thấy khuôn mặt nên khi lên tiếng cũng nâng cái ô lên. Đó là một chàng trai trẻ tuổi có khuôn mặt tuấn mỹ, da dẻ như ngọc, ánh mắt lập lòe như điện.\n\n“Hả?”\n\nMột tiếng hô kinh ngạc vang lên.\n\nCái nhíu mày của người đàn ông trung niên giãn ra, khuôn mặt thư thái: “Không trách khí tức so với người khác yếu hơn nhiều … Hóa ra ngươi không phải là một trong lục đại cung phụng Giam Thiên Ti, nếu vậy thì ngươi hẳn là quan viên Thần Đô Giam rồi.”\n\nHai tay viên quan trẻ tuổi mặc hắc y có khuôn mặt tuấn mỹ vốn run nhè nhẹ không thể nhìn thấy, động tác khi trước dường như y phải tập trung dũng khí mới làm được. Lúc này, y nghe thấy người đàn ông trung niên nhận xét khí tức của mình yếu hơn những người cầm ô khác rất nhiều thì lửa giận đã bốc lên trong mắt, hơi thở bỗng nhanh hơn một chút.\n\nÁnh mắt người đàn ông trung niên lướt qua người y lại nhìn vào cô gái mặc váy trắng. Người nọ mỉm cười với cô gái rồi nói: “Ở cái tuổi này mà đã vượt qua cảnh giới thứ tư nửa bước thì y cũng có thể được coi là kẻ tài tuấn hiếm thấy tại vương triều các ngươi rồi.”\n\nCô gái mặc váy trắng tươi cười, trên má hiện hai lúm đồng tiền tươi tắn: “Tiên sinh nói không sai.”\n\n“Có lẽ y ngưỡng mộ ngươi, muốn lưu lại trong ngươi chút ấn tượng mà thôi.” Người đàn ông trung niên ngắm thật kỹ cô gái mặc váy trắng: “Có chút đáng tiếc hay không?”\n\n“Ngươi … có ý gì?” Viên quan trẻ tuổi bỗng nhiên trắng bệch, mồ hôi túa ra làm ướt quần áo, trong lòng đột nhiên cảm thấy có gì đó không hay.\n\nCô gái quay đầu nhìn y, nụ cười mỉm làm cho người khác thấy như thể không chút ác cảm với vị trai trẻ anh tuấn này, nhưng một giọt mưa đang rơi xuống bỗng nhiên đứng im.\n\nCái giọt nước này bắt đầu gia tăng tốc độ, gia tốc đạt tới tình trạng khủng bố, trong quá trình gia tốc bỗng bị kéo dài thành một thanh tiểu kiếm mỏng manh.\n\n“Xuy” một tiếng vang nhỏ.\n\nBên trong cái ô đen dính đầy máu, viên quan trẻ tuổi tuấn mỹ đã đầu lìa khỏi cổ. Khi cái ô rơi xuống, cặp mắt y vẫn mở trừng trừng như không thể tin đó là chuyện thực.\n\n“Hảo khí phách!”\n\nNgười đàn ông trung niên vỗ tay hoan hô, “Ngay cả người Thần Đô giam cùng hoạt động cũng một kiếm giết chết. Quả nhiên Dạ Ti Thủ có khí phách, nhưng mà vì một câu không thuận tâm ý mà giết chính một tên tu hành hiếm có của mình, hình như Dạ Ti Thủ không có tấm lòng rộng rãi.”\n\nCô gái giễu cợt đáp lại: “Đàn bà cần gì tấm lòng to, ngực to là đủ.”', null, null, '2020-11-12 16:30:15', '2020-11-12 16:30:15');
INSERT INTO `T_CHAPTERS` VALUES ('2', '1', 'Chương 1-2: Dư nghiệt kiếm lô (2)', 'Người đàn ông ngẩn ra, người nọ không ngờ cô gái sẽ đáp lại như vậy.\n\n“Có lý.”\n\nNgười nọ cười cợt tự giễu, “Nhân vật như Dạ Ti Thủ, dù có làm gì hay nói gì cũng đều chính xác, không cần để ý người khác nghĩ gì.”\n\nBờ mi cô gái khẽ chớp, đôi môi khẽ mở nhưng đúng lúc này thị cảm ứng được gì đó, chân mày cau lại, không nói thêm gì nữa.\n\nNét mặt vui vẻ của người đàn ông cũng biến mất, vài nếp nhăn ở khóe mắt cũng bị vài ánh huỳnh quang kỳ dị xóa mất, da dẻ đầu tóc phát sáng màu ngọc, một luồng nhiệt khí bốc lên cuồn cuộn khiến cho những giọt mưa trên trời rơi xuống biến thành hơi nước trắng xóa. Một luồng sát khí nồng đậm bắt đầu tràn ngập tiểu viện.\n\n“Mặc dù chủ tu không giống nhau nhưng người tu hành trong thiên hạ đều phân chia cảnh giới thực lực thành cửu cảnh, mỗi cảnh lại chia làm ba, hoàng đế bệ hạ của các ngươi hiện tại rốt cuộc đã đến cảnh giới nào rồi?” Lúc cô gái với thân phận siêu nhiên hành lễ, người đàn ông này không hoàn lễ mà vào lúc này người nọ lại vái chào thật sâu thật nghiêm túc, hỏi cũng trang nghiêm.\n\n“Ta không có bụng dạ rộng rãi cho nên không có lợi ích gì sẽ không trả lời câu hỏi của ngươi.” Cô gái bình thản nhìn lại, ngữ khí không cần thương thảo thốt lên, “Một người một vấn đề thôi.”\n\nNgười đàn ông trung niên trầm ngâm một chút bèn ngẩng đầu: “Được.”\n\nCô gái không cần rào đó mà thẳng thắn hỏi: “Đệ tử kiếm lô đều là vong mệnh kiếm, đến tính mạng của bản thân cũng không quan tâm nhưng ba năm tiềm phục ngươi không ám sát kẻ tu hành của triều đình, không âm thầm kéo bè kết đảng cũng chẳng tìm cách đánh cắp điển tịch tu hành của triều đình ta, đến cùng ngươi định làm gì?”\n\nNgười nọ ngắm nghía cô gái, khẽ thở dài một tiếng: “Bí khố vũ tàng của đám tu hành các ngươi cho là mạnh đi nữa thì vật người đó lưu lại mạnh được bao nhiêu?”\n\nNgười đàn ông nói rất ngắn gọn, thậm chí còn không nói rõ danh tính người kia nhưng hai chữ này giống như là cấm kị vậy bởi dù cảnh huyết tinh một kiếm chém đầu kia không làm cho năm quan viên che ô đen ngoài viện chút xúc động nào, mà khi nghe thấy câu này xong, các chiếc ô đều đồng loạt khẽ rung, nước mưa ở trên mặt ô bị chấn động tung bay phấp phới.\n\nCô gái tỏ ra không thích, thị cười lạnh: “Đã nhiều năm rồi các ngươi vẫn chưa từ bỏ ý định, còn muốn nhìn xem người nọ có để lại cái gì hay không sao?”\n\nNgười đàn ông không đáp lại, cặp mắt nhìn chằm chằm vào mắt cô gái như mong đợi câu trả lời tiếp theo.\n\nCô gái thấy người đàn ông càng lúc càng tỏa ra mị lực, cô bỗng có chút đồng cảm với đối phương bèn nhẹ nhàng thốt lên: “Năm năm trước, thánh thượng đã tới thất cảnh thượng phẩm, trong năm năm này không một lần rat ay, không biết câu trả lời ngươi có thỏa mãn?”\n\n“Năm năm trước đã là thất cảnh thượng phẩm, có năm năm để phá kính có lẽ cũng đủ rồi. Nói vậy thì khả năng có thể đã tới bát cảnh ư?” Từ sâu trong mắt người đàn ông là thất vọng và chán nản nhưng một khắc sau đã biến đâu mất mà trở thành kiếm ý sắc bén.\n\nToàn thân người đàn ông sáng lên, giống như một thanh bảo kiếm tuyệt thế cất giấu nhiều năm mà bỗng nhiên rời vỏ.\n\nDây leo khô héo bám trên tường và nóc nhà bỗng nhiên bị khí tức sắc bén chém thành từng đoạn nhỏ bay lên.\n\n“Mời!”\n\nNgười đàn ông hít sâu một hơi, trong mắt người này dường như chỉ còn cô gái mặc váy trắng đối diện mà thôi.\n\n“Kiếm lô đệ thất đồ Triệu Trảm lĩnh giáo Thu Thủy Kiếm Dạ Ti Thủ!”\n\nKhi người đàn ông xin thỉnh giáo, cô gái vẫn im lặng trầm mặc không có phản ứng gì nhưng năm quan viên mặc hắc y ở bên ngoài đều khẽ hô, thân ảnh đột nhiên phân ra năm góc, chiếc ô đen xoay tròn kịch liệt.\n\nChiếc ô xoay tròn như tấm khiên, nương theo vòng xoay bắn ra không phải là những giọt mưa mà là vô số kình khí.\n\nẦm!\n\nTòa tiểu viện phồng lên như được làm bằng giấy rồi nổ thành vô số mảnh nhỏ.\n\nNhững tiếng kêu dưới chiếc ô vang lên, những mảnh vụn văng ra ẩn chứa sức mạnh kinh người làm cho đế giày năm vị quan viên cầm ô cọ xát vào đá phiến vang lên những tiếng chói tai.\n\nKình khí dày đặc hợp thành bức tường không kẽ hở, rất ít mảnh vụn xuyên qua được. Lửa cháy rừng rực bị ép bốn phía làm cho bốc lên ngang trời, từ xa nhìn tựa như thấy một cái lò thật lớn đột nhiên hiện ra giữa trời.\n\nỞ trung tâm của cái lò, trên tay Triệu Trảm chẳng biết từ lúc nào xuất hiện một thanh tiểu kiếm màu đỏ thẫm.\n\nThanh kiếm dài không quá hai thước, từ thân kiếm đến mũi kiếm phóng ra chân hỏa rừng rực dài đến mấy mét.\n\nCô gái mặc váy trắng được gọi là Dạ Ti Thủ cũng biến mất, chỉ có ngàn tia vạn tia mưa bụi như vô số thanh kiếm lao đến người nọ như một cái lồng.\n\n…\n\nKhi năm vị quan viên cầm ô ra tay thì mười mấy tên kiếm sư cầm các loại kiếm cũng quỷ mị tràn vào trong ngõ hẻm.\n\nTrên người những kiếm sư này có khí tức giống với năm vị quan cầm ô kia. Dưới trời mưa mà những hạt mưa cứ như là có sinh mạng tránh xa bọn họ, thân hình họ cứ như là có một tầng không khí trong suốt cách ly thế giới bên ngoài.\n\nMột màn như vậy đã cho thấy họ giống như năm vị quan viên cầm ô kia, đều là hiếm thấy, bản thân làm người ta không cách nào tưởng tượng được thủ đoạn của người tu hành.\n\nLúc này, trong tiểu viện vang lên những tiếng nổ liên tiếp, mặt đất xung quanh đầy vết lõm bởi chấn động do thủy châu bắn ra không ngừng. Họ căn bản không biết được tình hình giao thủ bên trong nhưng sắc mặt càng lúc càng trắng, một hôi túa ra lòng bàn tay ngày càng nhiều.\n\nTrước đây họ tưởng đã hiểu rõ trình độ kiếm lô nước Triệu ra sao nhưng bây giờ thì họ biết rằng đánh giá của bản thân còn quá thấp.\n\nChỉ là phút chốc thôi, ngắn đến độ dân chúng xung quanh đều tưởng là sét đánh mà không nghĩ là gì khác, màn ô đen vây quanh tiểu viện bỗng nhiên phát ra tiếng nổ khác thường.\n\nMột cái ô không chịu nổi nữa bị đẩy bay đi gần trăm mét.\n\nQuan viên mặc hắc y đeo kiếm không vỏ vây bên ngoài đồng loạt biến sắc, bốn kiếm sư phía sau cái ô bị chấn bay đồng loạt quát lên một tiếng, rút kiếm giơ ra phía trước.\n\nĐinh đinh đinh đinh bốn thanh âm vang lên, bốn thanh kiếm kia bị uốn thành vòng cung, bốn vị kiếm sư bị chấn động. Họ định cố cự lại nhưng chỉ trong chớp mắt cả bốn vị đều phun ra một búng máu, thân hình xụi lơ bay về phía sau như chim gãy cánh.\n\nTừ vị trí chiếc ô bị chấn bay, một luồng sóng khí bắn ra, nó xuyên qua vườn rau, phá vỡ hai bức tường, xuyên qua con đường rộng, đánh vào một cửa hàng dầu vừng.\n\nẦm một tiếng vang lên.\n\nCửa đi của cửa hàng bị phá vỡ thành vô số mảnh nhỏ, lại còn làm cho nửa cửa hàng bị sập xuống, các cấu kiện rơi rầm rầm xuống đất làm náo động lớn.\n\n“Mưa gió ông trời rơi xuống có mắt không tròng, rơi gì mà nhanh thế! Phá mất cửa hàng ta rồi!”\n\nMột tiếng thét chói tai từ bên trong cửa hàng vang lên, một người đàn bà trung niên đau đớn cầm cái muôi khua khoắng từ trong cửa hàng vọt ra, bộ như muốn đánh người nhưng khi thấy cảnh tượng trước mắt thì cái muôi dầu rơi ra khỏi tay, lại một tiếng thét chói tai hơn.\n\n“Giam Thiên Ti phá án!”\n\nMột kiếm sư hắc y miệng trào máu tươi bị chấn bay tới cái bàn đá xanh trước cửa hàng, y nghe thấy người đàn bà thét lên thì cắn răng, cố gượng chống thanh kiếm uốn cong để đứng dậy. Y quát lên một tiếng, sát ý lạnh thấu xương làm cho người đàn bà kia run rẩy, ngừng kêu.\n\nNhưng cũng vào lúc này, làm cho khuôn mặt thảm hại của kiếm sư sững sờ là bên trong cửa hàng dầu vừng bị sụp có một thiếu niên xách bình dầu đi ra. Thiếu niên nhiều lắm là mười ba mười bốn tuổi, khuôn mặt non nớt dính đầy tro bụi nhưng không có vẻ sợ hãi chút nào.\n\nĐứa trẻ tỏ ra hiếu kỳ, ánh mắt trong trẻo nhìn kiếm sư hắc y, nhìn qua toàn thân kiếm sư rồi nhìn về bức tường bị phá hủy kia.\n\nỞ trong tầm mắt của nó, một cô gái mặc váy trắng dáng người uyển chuyển từ bên trong đi ra.\n\n“Hậu táng hắn.”\n\nToàn thân cô gái ướt đẫm. Thị như thể mệt mỏi cực điểm lắm rồi. Mấy cái ô màu đen tụ lại che mưa cho cô ta mà thị thì chỉ nhạt giọng thốt lên ba tiếng.', null, null, '2020-11-12 16:38:14', '2020-11-12 16:38:14');

-- ----------------------------
-- Table structure for T_DATA
-- ----------------------------
DROP TABLE IF EXISTS `T_DATA`;
CREATE TABLE `T_DATA` (
  `DATA_ID` int(8) NOT NULL,
  `CATEGORY_ID` int(3) NOT NULL,
  `DATA_NAME` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL,
  `INFO` mediumtext COLLATE utf8_unicode_ci,
  `AUTHOR` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LINK_IMG` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `STATUS` int(1) DEFAULT NULL,
  `INSERT_DATE` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `DELETE_DATE` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`DATA_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of T_DATA
-- ----------------------------
INSERT INTO `T_DATA` VALUES ('1', '2', 'Vô Thượng Sát Thần\r\n', 'Cái gì là thiên tài? Vô luận bất luận cái gì chiến kỹ, công pháp đều có thể hoàn toàn lĩnh ngộ, cái này liền là thiên tài chân chính!<br>Tiêu Phàm mang theo Thần Bí Thạch Đầu trọng sinh Chiến Hồn Đại Lục, thức tỉnh kỳ dị chiến hồn, Nhất Đại Sát Thần, đột nhiên xuất hiện, ai dám tranh phong?<br>Vì hồng nhan, hắn có thể huyết nhuộm thanh thiên, thẳng lên cửu tiêu ôm minh nguyệt. Vì huynh đệ, hắn có thể đồ thi trăm vạn, dám cho thiên địa biến hoang tàn!', null, 'vo_thuong_sat_than.jpg', '1', '2020-10-20 12:28:58', '2020-10-20 12:28:58');

-- ----------------------------
-- Table structure for T_STORIES
-- ----------------------------
DROP TABLE IF EXISTS `T_STORIES`;
CREATE TABLE `T_STORIES` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `DESCRIPTION` text COLLATE utf8_unicode_ci,
  `CATEGORY_ID` int(11) DEFAULT NULL,
  `AUTHOR_NAME` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `CHAPTER_COUNT` int(11) DEFAULT NULL,
  `STATUS` int(1) DEFAULT NULL,
  `LINK_IMG` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `PUBLIC_FLG` int(1) DEFAULT NULL,
  `DELETE_FLG` date DEFAULT NULL,
  `CREATE_USER_ID` varchar(12) COLLATE utf8_unicode_ci DEFAULT NULL,
  `INSERT_DATETIME` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `UPDATE_DATETIME` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- ----------------------------
-- Records of T_STORIES
-- ----------------------------
INSERT INTO `T_STORIES` VALUES ('1', 'KIẾM VƯƠNG TRIỀU', 'Kể từ khi liên tiếp diệt ba vương triều lớn Hàn, Triệu, Ngụy, Vương Triều Đại Tần đã đón nhận một lượng tu hành giả lớn chưa từng có từ trước tới giờ. Mỗi người trong tầng tầng, lớp lớp người tu hành đều lấy danh phận người Tần mà hãnh diện.\n\nĐinh Trữ, một thiếu niên xuất thân Tần Quốc không thể nghi ngờ, sống tại Trường Lăng thành lại mỗi ngày suy tính làm sao phá tan Vương Triều Đại Tần, giết chết Tần Hoàng Đế đã tu hành đến Đệ Bát Cảnh, cảnh giới xưa này chưa từng có người luyện thành...', '1', 'Vô Tội', null, '1', null, '0', null, null, '2020-11-12 13:51:01', '2020-11-12 13:51:01');
