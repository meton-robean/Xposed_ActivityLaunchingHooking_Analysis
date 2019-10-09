import pygal 
#from IPython.display import SVG

labels = ['run-1', 'run-2', 'run-3', 'run-4', 'run-5']


bar_chart = pygal.HorizontalStackedBar()


#map(function, iterable, ...)
#第一个参数 function 以参数序列中的每一个元素调用 function 函数，返回包含每次 function 函数返回值的新列表。
bar_chart.x_labels = map(str, labels)

#filemanager MainActivity
# bar_chart.title = "Filemanager MainActivity"
# bar_chart.add('onCreate-onStart', [1734, 1563, 1583, 1637, 1580])
# bar_chart.add('onStart-onResume', [14, 23, 23, 10, 10])
# bar_chart.add('onResume-running', [140, 117, 114, 122, 111])
# bar_chart.add('onRestart-onStart',[20, 24, 24, 21, 17])
# bar_chart.add('onStart-onResume', [71, 178, 26, 18, 22])
# bar_chart.add('onResume-running', [85, 65, 79, 98, 103])
# bar_chart.render_to_file('./pics/filemanager-MainActivity.svg')
# bar_chart.render_to_png('./pics/filemanager-MainActivity.png')

#filemanager Preference.java
# bar_chart.title = "Filemanager Preference Activity"
# bar_chart.add('onCreate-onStart', [100, 68, 59, 67, 54])
# bar_chart.add('onStart-onResume', [1, 2, 1, 2, 3])
# bar_chart.add('onResume-running', [2, 1, 2, 1, 1])
# bar_chart.add('onRestart-onStart',[2, 0, 0, 0, 1])
# bar_chart.add('onStart-onResume', [1, 1, 0, 1, 0])
# bar_chart.add('onResume-running', [2, 2, 2, 2, 5])
# bar_chart.render_to_file('./pics/filemanager-Preference.svg')
# bar_chart.render_to_png('./pics/filemanager-Preference.png')


#toutiao MainActivity
# bar_chart.title = "Toutiao Main Activity"
# bar_chart.add('onCreate-onStart', [766, 748, 790, 753, 772 ])
# bar_chart.add('onStart-onResume', [189, 182, 190, 188, 191])
# bar_chart.add('onResume-running', [12, 12, 13, 15, 12])
# bar_chart.add('onRestart-onStart',[1, 2, 1, 4, 1])
# bar_chart.add('onStart-onResume', [4, 5, 5, 5, 6])
# bar_chart.add('onResume-running', [30, 5, 6, 16, 10 ])
# bar_chart.render_to_file('./pics/Toutiao-Main.svg')
# bar_chart.render_to_png('./pics/Toutiao-Main.png')

#toutiao MainActivity
# bar_chart.title = "Toutiao VideoContent Activity"
# bar_chart.add('onCreate-onStart', [193, 78, 169, 153, 165])
# bar_chart.add('onStart-onResume', [7, 5, 4, 5, 10])
# bar_chart.add('onResume-running', [6, 6, 5, 4, 4])
# bar_chart.add('onRestart-onStart',[0, 0, 0, 0, 0])
# bar_chart.add('onStart-onResume', [5, 19, 7, 3, 4])
# bar_chart.add('onResume-running', [12, 9, 15, 7, 10 ])
# bar_chart.render_to_file('./pics/Toutiao-VideoContent.svg')
# bar_chart.render_to_png('./pics/Toutiao-VideoContent.png')


# #Ghost MainActivity
# bar_chart.title = "Ghost Main Activity"
# bar_chart.add('onCreate-onStart', [451, 460, 510, 481, 453])
# bar_chart.add('onStart-onResume', [1, 1, 1, 2, 1])
# bar_chart.add('onResume-running', [1, 1, 1, 1, 2])
# bar_chart.add('onRestart-onStart',[3, 14, 5, 3, 3])
# bar_chart.add('onStart-onResume', [17, 8, 16, 14, 10])
# bar_chart.add('onResume-running', [2, 2, 2, 1, 2])
# bar_chart.render_to_file('./pics/Ghost-Main.svg')
# bar_chart.render_to_png('./pics/Ghost-Main.png')

#Ghost SettingActivity
bar_chart.title = "Ghost Setting Activity"
bar_chart.add('onCreate-onStart', [93, 69, 51, 65, 63])
bar_chart.add('onStart-onResume', [4, 5, 3, 4, 18])
bar_chart.add('onResume-running', [4, 6, 4, 5, 30])
bar_chart.add('onRestart-onStart',[15, 26, 7, 4, 5])
bar_chart.add('onStart-onResume', [12, 13, 9, 7, 13])
bar_chart.add('onResume-running', [11, 7, 14, 4, 10])
bar_chart.render_to_file('./pics/Ghost-SettingActivity.svg')
bar_chart.render_to_png('./pics/Ghost-SettingActivity.png')

