# mapreduce_join
use the mapreduce to make a table join the other table 
customer

客户编号	姓名	地址	电话
1	hanmeimei	ShangHai	110
2	leilei	BeiJing	112
3	lucy	GuangZhou	119
** order**

订单编号	客户编号	订单金额
1	1	50
2	1	200
3	3	15
4	3	350
5	3	58
6	1	42
7	1	352
8	2	1135
9	2	400
10	2	2000
11	2	300
结果

客户编号	订单编号	订单金额	姓名	地址	电话
1	1	50	hanmeimei	ShangHai	110
1	2	200	hanmeimei	ShangHai	110
1	6	42	hanmeimei	ShangHai	110
1	7	352	hanmeimei	ShangHai	110
2	8	1135	leilei	BeiJing	112
2	9	400	leilei	BeiJing	112
2	10	2000	leilei	BeiJing	112
2	11	300	leilei	BeiJing	112
3	3	15	lucy	GuangZhou	119
3	4	350	lucy	GuangZhou	119
3	5	58	lucy	GuangZhou	119
