export declare function invoke(cmd: string): Promise<{
    error: string;
    stdout: string;
    stderr: string;
}>;
