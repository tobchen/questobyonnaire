import type { Metadata } from "next"
import "./globals.css"
import Link from "next/link"

export const metadata: Metadata = {
    title: "Questobyonnaire",
    description: "Generated by create next app",
}

export default function RootLayout({
    children,
}: {
    children: React.ReactNode
}) {
    return (
        <html lang="en">
            <body className="max-w-3xl mx-auto p-2 bg-emerald-700 font-mono">
                <header className="text-white">
                    <h1 className="text-5xl font-bold">
                        <img src="/logo-mono.svg" className="inline-block w-12 h-12 mr-2" />
                        Questobyonnaire
                    </h1>

                    <nav>
                        <Link href="/" className="font-bold hover:underline">Home</Link>
                        |
                        <Link href="/editor" className="font-bold hover:underline">Editor</Link>
                    </nav>
                </header>

                {children}
            </body>
        </html>
    )
}
