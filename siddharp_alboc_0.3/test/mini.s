        .globl  main                    
main:   pushl   %ebp                    # int main ;
        movl    %esp,%ebp               
        .data                           
        movl    $0,%eax                 # 0
        jmp     .exit$main              # Return-Statement
.exit$main:                                
        popl    %ebp                    
        ret                             # end main
