import sys
import argparse
import MLparser

def compiler(source, tokens, output):
    tree, symbolTable = MLparser.parser(source, tokens)
    generator(tree, symbolTable, output)
    # Call parser

def generator(tree, symbolTable, output):

    pass

def begin(tree, symbolTable, output):
    # add all variables to .data part
    #fix this when karro sends out the generator for random names
    #currently cant tell if variables are properly initialized
    outputFile = open(output, 'w+')
    outputFile.write(".data\n");
    for i in symbolTable:
	outputFile.write(i+": .word 0\n")
    outputFile.write(".text\n")
    finish(outputFile)
    pass
def read(tree, symbolTable, output, readId):
    with open(output,"a") as outputFile:
	outputFile.write("\nli $v0,5\nsyscall\nla $t0, "+readId+"\nsw $v0, 0($t0)\n")
    	finish(outputFile)
    pass
def write(tree, symbolTable, output, writeId):
    with open(output,"a") as outputFile:
	outputFile.write("\nli $v0,1\nlw $a0,"+writeId+"\nsyscall\n")
	finish(outputFile)
    pass
def assignIntLit(tree, symbolTable, output, ident, value):
    with open(output,"a") as outputFile:
	outputFile.write("\nli $t0,"+value+"\nla $t1,"+ident+"\nsw $t0, 0($t1)\n")
	finish(outputFile)
    pass
def assignVariable(tree, symbolTable, output, ident1, ident2):
    with open(output,"a") as outputFile:
        outputFile.write("\nla $t0,"+ident2+"\nla $t1,"+ident1+"\nsw $t0, 0($t1)\n")
        finish(outputFile)
    pass
def infixAdd(tree, symbolTable, output, ident1, ident2, ident3):
    with open(output,"a") as outputFile:
	if(ident2.isdigit()):
		outputFile.write("\nli $s0, "+ident2+"\nla $t1, 0($s0)\n")
	else:
		outputFile.write("\nla $s0, "+ident2+"\nlw $t1, 0($s0)\n")
	if(ident3.isdigit()):
	    	outputFile.write("li $s0, "+ident3+"\nla $t2, 0($s0)\n")
	else:
    		outputFile.write("la $s0, "+ident3+"\nlw $t2, 0($s0)\n")

	outputFile.write("add $t0, $t1, $t2\nla $s0,"+ident1+"\nsw $t0, 0($s0)\n")
    pass
def infixSub(tree, symbolTable, output, ident1, ident2, ident3):
    with open(output,"a") as outputFile:
        if(ident2.isdigit()):
                outputFile.write("\nli $s0, "+ident2+"\nla $t1, 0($s0)\n")
        else:
                outputFile.write("\nla $s0, "+ident2+"\nlw $t1, 0($s0)\n")
        if(ident3.isdigit()):
                outputFile.write("li $s0, "+ident3+"\nla $t2, 0($s0)\n")
        else:
                outputFile.write("la $s0, "+ident3+"\nlw $t2, 0($s0)\n")

        outputFile.write("sub $t0, $t1, $t2\nla $s0,"+ident1+"\nsw $t0, 0($s0)\n")	
    pass
def process(tree, symbolTable, output):
    #This method shouldnt contain any mips. It should process identifiers and update the
    #symbol table as far as I know. How it works is a design decision we will have to make
    #so it is blank for now.
    pass
def finish(outputFile):
    outputFile.close()
    
 
if __name__ == "__main__":  # Only true if program invoked from the command line

    # Use the argparse library to parse the commandline arguments
    parser = argparse.ArgumentParser(description = "GroupX micro-language compiler")
    parser.add_argument('-t', type = str, dest = 'token_file',
                       help = "Token file", default = 'tokens.txt')
    parser.add_argument('source_file', type = str,
                        help = "Source-code file", default = 'tokens.txt')
    parser.add_argument('output_file', type = str, 
                        help = 'output file name')
    
    args = parser.parse_args()

    # Call the compiler function
    compiler(args.source_file, args.token_file, args.output_file)

