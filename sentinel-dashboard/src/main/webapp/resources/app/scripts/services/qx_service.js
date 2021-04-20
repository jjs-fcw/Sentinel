var app = angular.module('sentinelDashboardApp');

app.service('AuthorityManagement', ['$http', function ($http) {
  this.queryMachineRules = function (app, ip, port) {
    var param = {
      app: app,
      ip: ip,
      port: port
    };
    return $http({
      url: '/authorityManagement/user.json',
      params: param,
      method: 'GET'
    });
  };

  this.newRule = function (rule) {
    return $http({
        url: '/authorityManagement/user',
        data: rule,
        method: 'POST'
    });
  };

  this.saveRule = function (rule) {
    var param = {
        id: rule.id,
        userName: rule.userName,
        userPwd: rule.userPwd,
        nickName: rule.nickName,
        apps: rule.apps,
    };
    return $http({
        url: '/authorityManagement/user/' + rule.id,
        data: param,
        method: 'PUT'
    });
  };

  this.deleteRule = function (rule) {
      return $http({
          url: '/authorityManagement/user/' + rule.id,
          method: 'DELETE'
      });
  };

  this.checkRuleValid = function (rule) {
      if (rule.userName === undefined || rule.userName === '') {
          alert('用户名不能为空');
          return false;
      }
      if (rule.userPwd === undefined || rule.userPwd === '') {
          alert('密码不能为空');
          return false;
      }
      if (rule.nickName === undefined || rule.nickName === '') {
          alert('姓名不能为空');
          return false;
      }
      if (rule.apps === undefined || rule.apps === '') {
          alert('权限不能为空');
          return false;
      }
      return true;
  };
}]);
