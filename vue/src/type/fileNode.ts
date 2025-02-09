
// 定义 Node 类型
interface fileNode {
    id: number;         // id 可以是数字或 null
    name: string;              // name 是字符串
    path: string;              // path 是字符串
    type: 'folder' | 'file';   // type 只能是 'folder' 或 'file'
    size: number | null;       // size 可以是数字或 null
    owner: string | null;      // owner 可以是字符串或 null

    children: fileNode[] | null;
}






export type {fileNode}