        .data                           
        .globl  LF                      
LF:     .fill   1,4,0                   
        .text                           
        .globl  gcd                     
gcd:    enter   $0,$0                   # Start function gcd ;
.L0001:                                 # Start while-statement
        movl    8(%ebp),%eax            # a
        pushl   %eax                    
        movl    12(%ebp),%eax           # b
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setne   %al                     # Test !=
        cmpl    $0,%eax                 
        je      .L0002                  
                                        # Start If-statement
        movl    8(%ebp),%eax            # a
        pushl   %eax                    
        movl    12(%ebp),%eax           # b
        popl    %ecx                    
        cmpl    %eax,%ecx               
        movl    $0,%eax                 
        setl    %al                     # Test <
        cmpl    $0,%eax                 
        je      .L0004                  
        leal    12(%ebp),%eax           # b
        pushl   %eax                    
        movl    12(%ebp),%eax           # b
        pushl   %eax                    
        movl    8(%ebp),%eax            # a
        movl    %eax,%ecx               
        popl    %eax                    
        subl    %ecx,%eax               # Compute -
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        jmp     .L0003                  
.L0004:                                 # Else-Part
        leal    8(%ebp),%eax            # a
        pushl   %eax                    
        movl    8(%ebp),%eax            # a
        pushl   %eax                    
        movl    12(%ebp),%eax           # b
        movl    %eax,%ecx               
        popl    %eax                    
        subl    %ecx,%eax               # Compute -
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
.L0003:                                 # End If-Statement
        jmp     .L0001                  
.L0002:                                 # End while-statement
        movl    8(%ebp),%eax            # a
        jmp     .exit$gcd               # Return-Statement
.exit$gcd:                                
        leave                           
        ret                             # end gcd
        .globl  main                    
main:   enter   $8,$0                   # Start function main ;
        leal    LF,%eax                 # LF
        pushl   %eax                    
        movl    $10,%eax                # 10
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        movl    $35,%eax                # 35
        pushl   %eax                    # Push Parameter #1
        call    putchar                 # call putchar
        addl    $4,%esp                 # Remove parameter
        movl    $32,%eax                # 32
        pushl   %eax                    # Push Parameter #1
        call    putchar                 # call putchar
        addl    $4,%esp                 # Remove parameter
        leal    -4(%ebp),%eax           # v1
        pushl   %eax                    
        call    getint                  # call getint
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        leal    -8(%ebp),%eax           # v2
        pushl   %eax                    
        call    getint                  # call getint
        popl    %edx                    
        movl    %eax,(%edx)             #  = 
        movl    -8(%ebp),%eax           # v2
        pushl   %eax                    # Push Parameter #2
        movl    -4(%ebp),%eax           # v1
        pushl   %eax                    # Push Parameter #1
        call    gcd                     # call gcd
        addl    $4,%esp                 # Remove parameter
        pushl   %eax                    # Push Parameter #1
        call    putint                  # call putint
        addl    $4,%esp                 # Remove parameter
        movl    LF,%eax                 # LF
        pushl   %eax                    # Push Parameter #1
        call    putchar                 # call putchar
        addl    $4,%esp                 # Remove parameter
        movl    $0,%eax                 # 0
        pushl   %eax                    # Push Parameter #1
        call    exit                    # call exit
        addl    $4,%esp                 # Remove parameter
.exit$main:                                
        leave                           
        ret                             # end main
