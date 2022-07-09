describe('Aop', function () {
    let argPassingAdvice,
        argsToTarget,
        targetFnReturn,
        targetObj,
        executionPoints;

    beforeEach(function () {
        targetObj = {
            targetFn: function () {
                executionPoints.push('targetFn');
                argsToTarget = Array.prototype.slice.call(arguments, 0);
            }
        };
        executionPoints = [];

        argPassingAdvice = (targetInfo) => {
            targetFnReturn = targetInfo.fn.apply(this, targetInfo.args);
        };

        argsToTarget = [];
    });


    describe('Aop.around(fnName, advice, targetObj)', function () {
        it('타킷 함수를 호출 시 어드바이스를 실행하도록 한다', function () {
            let executeAdvice = false;
            const advice = function () {
                executeAdvice = true;
            };

            Aop.around('targetFn', advice, targetObj);
            targetObj.targetFn();
            expect(executeAdvice).toBe(true);
        });

        it('어드바이스가 타킷 호출을 래핑한다.', function () {
            const wrappingAdvice = (targetInfo) => {
                executionPoints.push('wrappingAdvice - 처음');
                targetInfo.fn();
                executionPoints.push('wrappingAdvice - 끝');
            };

            Aop.around('targetFn', wrappingAdvice, targetObj);
            targetObj.targetFn();
            expect(executionPoints).toEqual([
                'wrappingAdvice - 처음',
                'targetFn',
                'wrappingAdvice - 끝'
            ]);
        });

        it('마지막 어드바이스가 기존 어드바이스에 대해 실행되는 방식으로 체이닝할 수 있다.', function () {
            const adviceFactory = (adviceID) => {
                return (function (targetInfo) {
                    executionPoints.push('wrappingAdvice - 처음 ' + adviceID);
                    targetInfo.fn();
                    executionPoints.push('wrappingAdvice - 끝 ' + adviceID);
                });
            };

            Aop.around('targetFn', adviceFactory('안쪽'), targetObj);
            Aop.around('targetFn', adviceFactory('바깥쪽'), targetObj);

            targetObj.targetFn();
            expect(executionPoints).toEqual([
                'wrappingAdvice - 처음 바깥쪽',
                'wrappingAdvice - 처음 안쪽',
                'targetFn',
                'wrappingAdvice - 끝 안쪽',
                'wrappingAdvice - 끝 바깥쪽',
            ]);

        });

        it('어드바이스에서 타깃으로 일반 인자를 넘길수 있다.', function () {
            Aop.around('targetFn', argPassingAdvice, targetObj);
            targetObj.targetFn('a', 'b');
            expect(argsToTarget).toEqual([
                'a',
                'b',
            ]);
        });

        it('타깃의 반환값도 어드바이스에서 참조할 수 있다.', function () {
            Aop.around('targetFn', argPassingAdvice, targetObj);
            const returnValue = targetObj.targetFn();
            expect(returnValue).toEqual(targetFnReturn);
        });
    });
});