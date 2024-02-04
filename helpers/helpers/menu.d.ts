export declare const Ask: (query: string) => Promise<string>;
export type MenuItem = [string, () => Promise<boolean>];
export declare function Menu(header: string, menu: MenuItem[]): Promise<void>;
export declare function Sleep(ms: number): Promise<void>;
export declare function Error(message: string): Promise<false>;
