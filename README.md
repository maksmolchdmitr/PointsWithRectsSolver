Задача
  Даны прямоугольники на плоскости с углами в целочисленных координатах ([1..109],[1..109]).
  Требуется как можно быстрее выдавать ответ на вопрос «Скольким прямоугольникам принадлежит точка (x,y)?» И подготовка данных должна занимать мало времени.
UPD: только нижние границы включены => (x1<= x) && (x<x2) && (y1<=y) && (y<y2)
Пример
Прямоугольники: {(2,2),(6,8)}, {(5,4),(9,10)}, {(4,0),(11,6)}, {(8,2),(12,12)}
Точка-ответ: 
(2,2) -> 1
(12,12) -> 0
(10,4) -> 2
(5,5) -> 3
(2,10) -> 0
(2,8) -> 0



![image](https://github.com/maksmolchdmitr/PointsWithRectsSolver/assets/62752481/5d152cee-5e0a-4ab7-9c45-95a1c834c982)





Цели лабораторной работы
Реализовать три разных решения задачи
Выяснить при каком объеме начальных данных и точек какой алгоритм эффективнее.
Алгоритм перебора
Без подготовки. При поиске – просто перебор всех прямоугольников
Подготовка O(1), поиск O(N)
Алгоритм на карте
Сжатие координат и построение карты.
Подготовка O(N3), поиск O(logN)
Алгоритм на дереве
Сжатие координат и построение персистентного дерева отрезков 
Подготовка O(NlogN), поиск O(logN)
