var defaultData1 = [
      {
        text: '风控（xx）',
        href: 'group-manage.html',
        tags: ['3'],
        selectable: true,
        nodes: [
          {
            text: '人工初审组（小明）',
            href: 'group-manage.html',
            tags: ['0'],
          },
          {
            text: '人工初审组（小明）',
            href: 'group-manage.html',
            tags: ['0'],
          },
          {
            text: '人工初审组（小明）',
            href: 'group-manage.html',
            tags: ['0'],
          },
        ]
      },
      {
        text: '催收（xx）',
        href: 'group-manage.html',
        tags: ['3'],
        nodes: [
          {
            text: '人工初审组（小明）',
            href: 'group-manage.html',
            tags: ['0'],
          },
          {
            text: '人工初审组（小明）',
            href: 'group-manage.html',
            tags: ['0'],
          },
          {
            text: '人工初审组（小明）',
            href: 'group-manage.html',
            tags: ['0'],
          },
        ]
      },
      {
        text: '财务（xx）',
        href: 'group-manage.html',
        tags: ['3'],
        nodes: [
          {
            text: '人工初审组（小明）',
            href: 'group-manage.html',
            tags: ['0'],
          },
          {
            text: '人工初审组（小明）',
            href: 'group-manage.html',
            tags: ['0'],
          },
          {
            text: '人工初审组（小明）',
            href: 'group-manage.html',
            tags: ['0'],
          },
        ]
      },
      {
        text: '总办（xx）',
        href: 'group-manage.html',
        tags: ['2'],
        nodes: [
          {
            text: '客户服务（xxx）',
            href: 'group-manage.html',
            tags: ['2'],
            nodes: [
              {
                text: '客户一组 （xx）',
                href: 'group-manage.html',
                tags: ['0']
              },
              {
                text: '客户二组 （xx',
                href: 'group-manage.html',
                tags: ['0']
              }
            ]
          },
          {
            text: '运营管理部',
            href: 'group-manage.html',
            tags: ['2'],
            nodes: [
              {
                text: '推广组',
                href: 'group-manage.html',
                tags: ['0']
              },
              {
                text: '渠道管理',
                href: 'group-manage.html',
                tags: ['0']
              }
            ]
          },
        ]
      }
    ];

var defaultData = [
      {
        text: 'Parent 1',
        href: '#parent1',
        tags: ['4'],
        nodes: [
          {
            text: 'Child 1',
            href: '#child1',
            tags: ['2'],
            nodes: [
              {
                text: 'Grandchild 1',
                href: '#grandchild1',
                tags: ['0']
              },
              {
                text: 'Grandchild 2',
                href: '#grandchild2',
                tags: ['0']
              }
            ]
          },
          {
            text: 'Child 2',
            href: '#child2',
            tags: ['0']
          }
        ]
      },
      {
        text: 'Parent 2',
        href: '#parent2',
        tags: ['0']
      },
      {
        text: 'Parent 3',
        href: '#parent3',
         tags: ['0']
      },
      {
        text: 'Parent 4',
        href: '#parent4',
        tags: ['0']
      },
      {
        text: 'Parent 5',
        href: '#parent5'  ,
        tags: ['0']
      }
    ];


    var json = '[' +
      '{' +
        '"text": "Parent 1",' +
        '"nodes": [' +
          '{' +
            '"text": "Child 1",' +
            '"nodes": [' +
              '{' +
                '"text": "Grandchild 1"' +
              '},' +
              '{' +
                '"text": "Grandchild 2"' +
              '}' +
            ']' +
          '},' +
          '{' +
            '"text": "Child 2"' +
          '}' +
        ']' +
      '},' +
      '{' +
        '"text": "Parent 2"' +
      '},' +
      '{' +
        '"text": "Parent 3"' +
      '},' +
      '{' +
        '"text": "Parent 4"' +
      '},' +
      '{' +
        '"text": "Parent 5"' +
      '}' +
    ']';
