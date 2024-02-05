export declare const DEFAULT_BRANCH_NAME = 'main';
export declare function Clean(file: string): string;
export declare function GetBranchName(): Promise<string | undefined>;
export declare function ReadBranchName(): Promise<string | false>;
export declare function PickBranchToContinue(): Promise<void>;
