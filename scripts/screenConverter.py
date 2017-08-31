targetf = open('c:/Users/Dennis Chan/Desktop/scripts/test.txt', 'r')
output = open('c:/Users/Dennis Chan/Desktop/scripts/testout.txt', 'w')

row = 0

output.write("d::writing.\n")

for item in targetf:
    onNewField = False
    writeString = ''
    startCol = -1
    spaces = 0
    output.write("d::"+item+"\n")
    for x in range(1, 25):
        currentChar = item[x:(x+1)]
        output.write("d::"+currentChar+"\n")
        if currentChar == '|':
            if onNewField:
                output.write(writeString+','+row+','+startCol+','+spaces)
                onNewField = False
        elif not currentChar == ' ':
            onNewField = True
            writeString = writeString + currentChar
            spaces = spaces + 1
        elif currentChar == 'X':
            onNewField = True
            spaces = spaces + 1
        else:
            if onNewField:
                output.write(writeString+','+row+','+startCol+','+spaces)
                onNewField = False
            writeString = ''
            spaces = 0
            startCol = x + 1
    row = row + 1

targetf.close()
output.close()
