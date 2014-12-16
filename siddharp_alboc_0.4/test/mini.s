        .globl  main                    
main:   enter   $0,$0                   # Start function main ;
        movl    $0,%eax                 # 0
        jmp     .exit$main              # Return-Statement
.exit$main:                                
        leave                           
        ret                             # end main
