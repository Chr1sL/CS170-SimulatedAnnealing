import random

n = 10
max = 99.999
k = 3

#k = random.randint(3, int(n/2))
if (n == 10):
    k = 3
elif (n == 20):
    k = 5
else:
    k = 7
max = round(random.uniform(78, 99.999),3)
dict = {}
x = 0
lister = [*range(n)]
random.shuffle(lister)
for i in lister:
    if x < k:
        dict[i] = x
        x += 1
    else:
        dict[i] = random.randint(0, k-1)
maxPer = max/k
# dict = {4:0, 5:0, 7:0, 1:1, 2:1, 3:1, 9:2, 0:2, 8:2, 6:2}

### function to calculate n choose k
def choose(n, k):
    if k == 0:
        return 1
    return (n * choose(n - 1, k - 1)) / k
    
### dict2 is has key = group number, value = list of people in that group
dict2 = {}
for i in dict:
    if dict[i] not in dict2:
        dict2[dict[i]] = [i]
    else:
        dict2[dict[i]].append(i)

for i in dict2:
  dict2[i].sort()

for i in dict2:
    for y in dict2[i]:
        print(str(y) + " " + str(i))
        
print("")
        
### final is nested list that will hold output
#   in form 
#   [
#        [person i, 
#            [person j, [happines, stress, chosenBool]], 
#            [person j+1, [happiness, stress, chosenBool]], 
#            ... 
#        ], 
#        [person i + 1, 
#            [....], 
#            ... 
#        ]
#   ]
final = []

### initiate final so that there are no [i,i] and each stress is randomized
#   originally, I randomizied the stress (line 44), but to show that update is properly
#   changing the ouputted pair, I just set the stress to 0 so only the outputed would change
for i in range(n):
    final.append([i])
    for j in range(i, n, 1):
        if (i != j):
            # final[i].append([j, [0,round(random.uniform(.001,99.999),3)]])
            # final[i].append([j, [0,0]])
            if (not random.randint(0,3)):       # eliminate if stress too high
                final[i].append([j, [round(random.uniform(.001,99.999),3), round(random.uniform(maxPer,99.999),3), False]])
            else:                               # eliminate if happiness too low
                final[i].append([j, [round(random.uniform(.001, 14.999), 3), round(random.uniform(10.001,maxPer),3), False]])
            # if (not random.randint(0,3)):       # eliminate if stress too high
            #     final[i].append([j, [round(random.uniform(14.999,99.999),3), round(random.uniform((max/2)-2, max/2),3), False]])
            # else:                               # eliminate if happiness too low
            #     final[i].append([j, [round(random.uniform(.001, 14.999), 3), round(random.uniform((max/3)-2, max/3),3), False]])
    
### this function updates dict2 (the one w the keys based on group number)
#       - basically looks at all the chosen output pairs and updates 
#         stress to random number such that the sum is less than max
#   im currently randomizing the happiness of the outputed pairs (see line 66)

sums = []
def update(dictionar):
    for i in dictionar:                         # loops through each breakout room
        oldList = []                            # oldList is just copy of list of people in breakout room i
        for y in dictionar[i]:
            oldList.append(y)
        temp2 = 0                               # temp 2 will keep track of current stress sum
        temp3 = 0
        currList = dictionar[i]
        for y in oldList:                       # loops through list of people in that breakout room
            currList.pop(0)                     # ^ loop basically just finds all possible pairs in that list
            for z in currList:
                temp = round(maxPer*random.uniform(.001,.999),3)
                while temp2 + temp > maxPer:    # makes sure that total sum does not surpass max  
                    temp = round(temp/random.randint(2,10),3)
                temp2 += temp
                num1 = temp
                num2 = round(random.uniform(15, 99.999), 3)     # chosen always has minimum happiness
                temp3 += num2
                if num1 <= 0:
                    num1 = 0.001
                final[y][z-y][1][1] = num1
                final[y][z-y][1][0] = num2
                final[y][z-y][1][2] = True
        sums.append(temp3) 
   
update(dict2)     

# for i in range(n):
#     for j in range(i, n, 1):
#         if (i != j):
#             if (not final[i][j-i][1][2]): 
#                 final[i][j-i][1][0] = 0
# print(sums)

print(n)
print(max)
for i in final:
    for j in i[1:]:
        print(str(i[0]) + " " + str(j[0]) + " " + str(j[1][0]) + " " + str(j[1][1]))