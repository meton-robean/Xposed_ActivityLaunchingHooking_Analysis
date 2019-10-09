# import numpy as np
# import matplotlib.pyplot as plt

#  #
# labels = ['onCreate-onStart', 'onStart-onResume', 'onResume-running',
#            'onRestart-onStart', 'onStart-onResume', 'onResume-running']

# #filemanager #1
# fracs = [130, 1, 2, 2, 1, 1]



# explode = [0, 0, 0, 0, 0, 0] # 0.1 凸出这部分，0 为不突出
# plt.axes(aspect=1)  # set this , Figure is round, otherwise it is an ellipse

# #autopct ，show percet
# plt.pie(x=fracs, labels=labels, explode=explode, autopct='%3.1f %%',
#         shadow=True, labeldistance=1.1, startangle = 90, pctdistance = 0.6
 
#         )

# '''
# labeldistance，文本的位置离远点有多远，1.1指1.1倍半径的位置
# autopct，圆里面的文本格式，%3.1f%%表示小数有三位，整数有一位的浮点数
# shadow，饼是否有阴影
# startangle，起始角度，0，表示从0开始逆时针转，为第一块。一般选择从90度开始比较好看
# pctdistance，百分比的text离圆心的距离
# patches, l_texts, p_texts，为了得到饼图的返回值，p_texts饼图内部文本的，l_texts饼图外label的文本
# '''
 
# plt.show()



# -*- coding: utf-8 -*-
import matplotlib.pyplot as plt
 
name_list = ['0-1','1-2','2-3','3-4', '4-5',
              '5-6', '6-7','7-8', '8-9', '9-10', '10-11', '11-12']
num_list = [26, 1, 101, 13, 116, 3, 4, 140, 36, 2, 12, 4]
num_list1 = [19, 1, 185, 2, 121, 20, 12, 206, 24, 1, 12,12]
num_list2 = [7, 11, 72, 1, 65, 5, 3, 124, 23, 1, 11, 5]

x =list(range(len(num_list)))
total_width, n = 0.6, 3
width = total_width / n
 
plt.bar(x, num_list, width=width, label='Toutiao',fc = 'y')
for i in range(len(x)):
    x[i] = x[i] + width
plt.bar(x, num_list1, width=width, label='FileManager',tick_label = name_list,fc = 'r')

for i in range(len(x)):
    x[i] = x[i] + width
plt.bar(x, num_list2, width=width, label='Ghost',tick_label = name_list,fc = 'b')

plt.legend()
plt.show()

