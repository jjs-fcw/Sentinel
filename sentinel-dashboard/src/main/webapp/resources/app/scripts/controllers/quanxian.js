var app = angular.module('sentinelDashboardApp');

app.controller('QuanxianCtl', ['$scope', '$stateParams', 'QuanxianService', 'ngDialog', 'MachineService',
  function ($scope, $stateParams, QuanxianService, ngDialog, MachineService) {
    //初始化
    $scope.app = $stateParams.app;
    $scope.rulesPageConfig = {
      pageSize: 10,
      currentPageIndex: 1,
      totalPage: 1,
      totalCount: 0,
    };
    getMachineRules();
    function getMachineRules() {
      QuanxianService.queryMachineRules("app", "A", "B").success(
        function (data) {
          if (data.code == 0 && data.data) {
            $scope.rules = data.data;
            $scope.rulesPageConfig.totalCount = $scope.rules.length;
          } else {
            $scope.rules = [];
            $scope.rulesPageConfig.totalCount = 0;
          }
        });
    };
    $scope.getMachineRules = getMachineRules;

    var degradeRuleDialog;
    $scope.editRule = function (rule) {
      $scope.currentRule = angular.copy(rule);
      $scope.degradeRuleDialog = {
        title: '编辑用户',
        type: 'edit',
        confirmBtnText: '保存'
      };
      degradeRuleDialog = ngDialog.open({
        template: '/app/views/dialog/quanxian-dialog.html',
        width: 680,
        overlay: true,
        scope: $scope
      });
    };

    $scope.addNewRule = function () {
      $scope.currentRule = {
        grade: 0,
        limitApp: 'default',
        minRequestAmount: 5,
        statIntervalMs: 1000,
      };
      $scope.degradeRuleDialog = {
        title: '新增用户',
        type: 'add',
        confirmBtnText: '新增'
      };
      degradeRuleDialog = ngDialog.open({
        template: '/app/views/dialog/quanxian-dialog.html',
        width: 680,
        overlay: true,
        scope: $scope
      });
    };

    $scope.saveRule = function () {
      if (!QuanxianService.checkRuleValid($scope.currentRule)) {
        return;
      }
      if ($scope.degradeRuleDialog.type === 'add') {
        addNewRule($scope.currentRule);
      } else if ($scope.degradeRuleDialog.type === 'edit') {
        saveRule($scope.currentRule, true);
      }
    };

    function parseDegradeMode(grade) {
        switch (grade) {
            case 0:
              return '慢调用比例';
            case 1:
              return '异常比例';
            case 2:
              return '异常数';
            default:
              return '未知';
        }
    }

    var confirmDialog;
    $scope.deleteRule = function (rule) {
      $scope.currentRule = rule;
      $scope.confirmDialog = {
        title: '删除账号',
        type: 'delete_user',
        attentionTitle: '请确认是否删除账号',
        attention: '账号: ' + rule.userName,
        confirmBtnText: '删除',
      };
      confirmDialog = ngDialog.open({
        template: '/app/views/dialog/confirm-dialog.html',
        scope: $scope,
        overlay: true
      });
    };

    $scope.confirm = function () {
      if ($scope.confirmDialog.type == 'delete_user') {
        deleteRule($scope.currentRule);
      } else {
        console.error('error');
      }
    };

    function deleteRule(rule) {
      QuanxianService.deleteRule(rule).success(function (data) {
        if (data.code == 0) {
          getMachineRules();
          confirmDialog.close();
        } else {
          alert('失败：' + data.msg);
        }
      });
    };

    function addNewRule(rule) {
      QuanxianService.newRule(rule).success(function (data) {
        if (data.code == 0) {
          getMachineRules();
          degradeRuleDialog.close();
        } else {
          alert('失败：' + data.msg);
        }
      });
    };

    function saveRule(rule, edit) {
      QuanxianService.saveRule(rule).success(function (data) {
        if (data.code == 0) {
          getMachineRules();
          if (edit) {
            degradeRuleDialog.close();
          } else {
            confirmDialog.close();
          }
        } else {
          alert('失败：' + data.msg);
        }
      });
    }
    // queryAppMachines();
    function queryAppMachines() {
      MachineService.getAppMachines($scope.app).success(
        function (data) {
          if (data.code == 0) {
            // $scope.machines = data.data;
            if (data.data) {
              $scope.machines = [];
              $scope.macsInputOptions = [];
              data.data.forEach(function (item) {
                if (item.healthy) {
                  $scope.macsInputOptions.push({
                    text: item.ip + ':' + item.port,
                    value: item.ip + ':' + item.port
                  });
                }
              });
            }
            if ($scope.macsInputOptions.length > 0) {
              $scope.macInputModel = $scope.macsInputOptions[0].value;
            }
          } else {
            $scope.macsInputOptions = [];
          }
        }
      );
    };
  }]);
