        .data
        .globl  LF
LF:     .fill   1,4,0                   # int LF
        .text
        .globl  test1                   
test1:  enter   $16,$0                  # Start function test1
        leal    -8(%ebp),%eax           # p1
        pushl   %eax                    
        leal    -4(%ebp),%eax           # v
        popl    %edx                    
        movl    %eax,(%edx)             #   =
        leal    -12(%ebp),%eax          # p2
        pushl   %eax                    
        leal    -8(%ebp),%eax           # p1
        popl    %edx                    
        movl    %eax,(%edx)             #   =
        leal    -16(%ebp),%eax          # p3
        pushl   %eax                    
        leal    -12(%ebp),%eax          # p2
        popl    %edx                    
        movl    %eax,(%edx)             #   =
        leal    -4(%ebp),%eax           # v
        pushl   %eax                    
        movl    $17,%eax                # 17
        popl    %edx                    
        movl    %eax,(%edx)             #   =
        leal    -8(%ebp),%eax           # p1
        movl    (%eax),%eax             #   *
        pushl   %eax                    
        movl    -8(%ebp),%eax           # p1
        movl    (%eax),%eax             # Compute prefix *
        pushl   %eax                    
        movl    $1,%eax                 # 1
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # Compute +
        popl    %edx                    
        movl    %eax,(%edx)             #   =
        leal    -12(%ebp),%eax          # p2
        movl    (%eax),%eax             #   *
        movl    (%eax),%eax             #   *
        pushl   %eax                    
        movl    -12(%ebp),%eax          # p2
        movl    (%eax),%eax             # Compute prefix *
        movl    (%eax),%eax             # Compute prefix *
        pushl   %eax                    
        movl    $2,%eax                 # 2
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # Compute +
        popl    %edx                    
        movl    %eax,(%edx)             #   =
        leal    -16(%ebp),%eax          # p3
        movl    (%eax),%eax             #   *
        movl    (%eax),%eax             #   *
        movl    (%eax),%eax             #   *
        pushl   %eax                    
        movl    -16(%ebp),%eax          # p3
        movl    (%eax),%eax             # Compute prefix *
        movl    (%eax),%eax             # Compute prefix *
        movl    (%eax),%eax             # Compute prefix *
        pushl   %eax                    
        movl    $3,%eax                 # 3
        movl    %eax,%ecx               
        popl    %eax                    
        addl    %ecx,%eax               # Compute +
        popl    %edx                    
        movl    %eax,(%edx)             #   =
        movl    $118,%eax               # 118
        pushl   %eax                    # Push parameter #1
        call    putchar                 # Call putchar
        addl    $4,%esp                 # Remove parameters
        movl    $61,%eax                # 61
        pushl   %eax                    # Push parameter #1
        call    putchar                 # Call putchar
        addl    $4,%esp                 # Remove parameters
        movl    -8(%ebp),%eax           # p1
        movl    (%eax),%eax             # Compute prefix *
        pushl   %eax                    # Push parameter #1
        call    putint                  # Call putint
        addl    $4,%esp                 # Remove parameters
        movl    $61,%eax                # 61
        pushl   %eax                    # Push parameter #1
        call    putchar                 # Call putchar
        addl    $4,%esp                 # Remove parameters
        movl    -12(%ebp),%eax          # p2
        movl    (%eax),%eax             # Compute prefix *
        movl    (%eax),%eax             # Compute prefix *
        pushl   %eax                    # Push parameter #1
        call    putint                  # Call putint
        addl    $4,%esp                 # Remove parameters
        movl    $61,%eax                # 61
        pushl   %eax                    # Push parameter #1
        call    putchar                 # Call putchar
        addl    $4,%esp                 # Remove parameters
        movl    -16(%ebp),%eax          # p3
        movl    (%eax),%eax             # Compute prefix *
        movl    (%eax),%eax             # Compute prefix *
        movl    (%eax),%eax             # Compute prefix *
        pushl   %eax                    # Push parameter #1
        call    putint                  # Call putint
        addl    $4,%esp                 # Remove parameters
        movl    LF,%eax                 # LF
        pushl   %eax                    # Push parameter #1
        call    putchar                 # Call putchar
        addl    $4,%esp                 # Remove parameters
.exit$test1:
        leave                           
        ret                             # End function test1
        .globl  test2                   
test2:  enter   $48,$0                  # Start function test2
        movl    $0,%eax                 # 0
        leal    -40(%ebp),%edx          # a[...]
        leal    (%edx,%eax,4),%eax      
        pushl   %eax                    
        movl    $2,%eax                 # 2
        negl    %eax                    # Compute prefix -
        popl    %edx                    
        movl    %eax,(%edx)             #   =
        leal    -44(%ebp),%eax          # a0
        pushl   %eax                    
        leal    -40(%ebp),%eax          # a
        popl    %edx                    
        movl    %eax,(%edx)             #   =
        leal    -48(%ebp),%eax          # a4
        pushl   %eax                    
        movl    $4,%eax                 # 4
        leal    -40(%ebp),%edx          # a[...]
        leal    (%edx,%eax,4),%eax      
        popl    %edx                    
        movl    %eax,(%edx)             #   =
        movl    $4,%eax                 # 4
        negl    %eax                    # Compute prefix -
        movl    -48(%ebp),%edx          # a4[...]
        leal    (%edx,%eax,4),%eax      
        pushl   %eax                    
        movl    $4,%eax                 # 4
        negl    %eax                    # Compute prefix -
        movl    -48(%ebp),%edx          # a4[...]
        movl    (%edx,%eax,4),%eax      
        pushl   %eax                    
        movl    $1,%eax                 # 1
        movl    %eax,%ecx               
        popl    %eax                    
        subl    %ecx,%eax               # Compute -
        popl    %edx                    
        movl    %eax,(%edx)             #   =
        movl    $97,%eax                # 97
        pushl   %eax                    # Push parameter #1
        call    putchar                 # Call putchar
        addl    $4,%esp                 # Remove parameters
        movl    $48,%eax                # 48
        pushl   %eax                    # Push parameter #1
        call    putchar                 # Call putchar
        addl    $4,%esp                 # Remove parameters
        movl    $61,%eax                # 61
        pushl   %eax                    # Push parameter #1
        call    putchar                 # Call putchar
        addl    $4,%esp                 # Remove parameters
        movl    -44(%ebp),%eax          # a0
        movl    (%eax),%eax             # Compute prefix *
        pushl   %eax                    # Push parameter #1
        call    putint                  # Call putint
        addl    $4,%esp                 # Remove parameters
        movl    LF,%eax                 # LF
        pushl   %eax                    # Push parameter #1
        call    putchar                 # Call putchar
        addl    $4,%esp                 # Remove parameters
.exit$test2:
        leave                           
        ret                             # End function test2
        .globl  main                    
main:   enter   $0,$0                   # Start function main
        leal    LF,%eax                 # LF
        pushl   %eax                    
        movl    $10,%eax                # 10
        popl    %edx                    
        movl    %eax,(%edx)             #   =
        call    test1                   # Call test1
        call    test2                   # Call test2
        movl    $0,%eax                 # 0
        pushl   %eax                    # Push parameter #1
        call    exit                    # Call exit
        addl    $4,%esp                 # Remove parameters
.exit$main:
        leave                           
        ret                             # End function main
