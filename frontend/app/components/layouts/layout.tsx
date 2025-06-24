import { Links, Meta, Scripts } from "react-router";

export default function Layout({ children }: { children: React.ReactNode }) {
    return(
        <html lang="ko">
            <head>
                <meta charSet="utf-8" />
                <meta name="viewport" content="width=device-width, initial-scale=1" />
                <Meta/>
                <Links/>
            </head>
            <body>
                {children}
                <Scripts/>
            </body>
        </html>
    );
}
