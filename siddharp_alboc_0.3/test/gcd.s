        .data                           
        .globl  LF                      
LF:     .fill   4                       
        .text                           
        .globl  gcd                     
gcd:    pushl   %ebp                    # int gcd ;
        movl    %esp,%ebp               
        .data                           
.L0001:                                 # Start while-statement
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setne   %al                     # Test !=
        cmpl    $0,%eax                 
        je      .L0002                  
.L0003:                                 # Start If-statement
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setl    %al                     # Test <
        cmpl    $0,%eax                 
        je      .L0003                  
        leal    12(%ebp),%eax           # b
.L0003:                                 
        jmp     .L0001                  
.L0002:                                 # End while-statement
        jmp     .exit$gcd               # Return-Statement
.exit$gcd:                                
        popl    %ebp                    
        ret                             # end gcd
        .globl  main                    
main:   pushl   %ebp                    # int main ;
        movl    %esp,%ebp               
        .data                           
-4(%ebp):
        .fill   4                       
-8(%ebp):
        .fill   4                       
        movl    $10,%eax                # 10
        leal    LF,%eax                 # LF
        leal    -4(%ebp),%eax           # v1
        leal    -8(%ebp),%eax           # v2
.exit$main:                                
        popl    %ebp                    
        ret                             # end main
