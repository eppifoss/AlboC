        .globl  main                    
main:   enter   $0,$0                   # Start function main ;
        movl    $35,%eax                # 35
        pushl   %eax                    # Push Parameter #1
        call    putchar                 # call putchar
        addl    $4,%esp                 # Remove parameter
        movl    $32,%eax                # 32
        pushl   %eax                    # Push Parameter #1
        call    putchar                 # call putchar
        addl    $4,%esp                 # Remove parameter
        call    getint                  # call getint
        pushl   %eax                    
        movl    $1,%eax                 # 1
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # Compute +
        pushl   %eax                    # Push Parameter #1
        call    putint                  # call putint
        addl    $4,%esp                 # Remove parameter
        movl    $10,%eax                # 10
        pushl   %eax                    # Push Parameter #1
        call    putchar                 # call putchar
        addl    $4,%esp                 # Remove parameter
        movl    $0,%eax                 # 0
        jmp     .exit$main              # Return-Statement
.exit$main:                                
        leave                           
        ret                             # end main
