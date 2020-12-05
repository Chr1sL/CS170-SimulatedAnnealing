import random



n=50
print(str(round(random.uniform(1, 99), 3)))
for i in range(n):
    for j in range(i, n, 1):
        if (i != j):
            print(str(i) + " " + str(j) + " " + str(round(random.uniform(1, 99), 3)) + " " + str(round(random.uniform(1, 99), 3)))
